package com.sn.manageservice.controller;

import com.sn.manageservice.controller.param.AccountParam;
import com.sn.manageservice.controller.param.AnimalParam;
import com.sn.manageservice.controller.param.RelationParam;
import com.sn.manageservice.controller.vo.*;
import com.sn.manageservice.pojo.Account;
import com.sn.manageservice.pojo.Animal;
import com.sn.manageservice.pojo.Relation;
import com.sn.manageservice.pojo.User;
import com.sn.manageservice.repostory.AccountRepository;
import com.sn.manageservice.repostory.AnimalRepository;
import com.sn.manageservice.repostory.RelationRepository;
import com.sn.manageservice.repostory.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther 向南
 * @date 2021/12/26 15:36
 * @description
 */
@Controller
@RequestMapping("/manage")
@Slf4j
public class ManageController {

    private static final String DATE_ALL_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private RelationRepository relationRepository;

    @PostMapping(value = "/appLogin")
    @ResponseBody
    public Response<AppLoginVo> appLogin(@RequestBody AccountParam accountParam) {
        AppLoginVo appLoginVo = new AppLoginVo();
        if (accountParam == null || StringUtils.isBlank(accountParam.getAccount()) || StringUtils.isBlank(accountParam.getPassword())){
            return Response.fail("参数为空", appLoginVo);
        }
        List<Account> accountByAccount = accountRepository.findAccountByAccount(accountParam.getAccount());
        if (CollectionUtils.isEmpty(accountByAccount)){
            return Response.fail("账号或密码错误", appLoginVo);
        }
        Account account = accountByAccount.get(0);
        if (!Objects.equals(account.getPassword(), accountParam.getPassword())){
            return Response.fail("账号或密码错误", appLoginVo);
        }
        List<Relation> relations = relationRepository.findRelationByAccountId(account.getId());
        List<Integer> animalIds = relations.stream().map(Relation::getAnimalId).collect(Collectors.toList());
        List<Animal> animals = animalRepository.findAllById(animalIds);
        Map<Integer, String> animalMap = animals.stream().collect(Collectors.toMap(Animal::getId, Animal::getAnimalName));
        List<AppRelationVo> appRelationVos = new ArrayList<>();
        for (Relation relation : relations) {
            AppRelationVo appRelationVo = new AppRelationVo();
            appRelationVo.setAnimalId(relation.getAnimalId());
            appRelationVo.setAnimalName(animalMap.get(relation.getAnimalId()));
            appRelationVo.setStartDateStr(getStringDateFormat(relation.getStartDate(),DATE_FORMAT));
            appRelationVo.setEndDateStr(getStringDateFormat(relation.getEndDate(),DATE_FORMAT));
            appRelationVos.add(appRelationVo);
        }
        appLoginVo.setAccount(account.getAccount());
        appLoginVo.setUserName(account.getUserName());
        appLoginVo.setAppRelationVos(appRelationVos);
        return Response.ok(appLoginVo);
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping(value = "/login")
    public String login(User user, HttpSession session, Model model) {
        if (StringUtils.isNotBlank(user.getUserName())) {
            User userInfo = userRepository.findUser(user.getUserName());
            if (userInfo == null || !Objects.equals(user.getPassword(), userInfo.getPassword())) {
                model.addAttribute("msg", "账号或者密码有误");
                return "login";
            }
            session.setAttribute("userName", user.getUserName());
            return "redirect:/manage/main?currentPage=1";
        } else {
            model.addAttribute("msg", "账号或者密码有误");
            return "login";
        }
    }


    @GetMapping("/main")
    public String main(Integer currentPage, Model model) {

        Pageable pageable = PageRequest.of(currentPage - 1, 10, Sort.Direction.DESC, "updateDate");
        Page<Account> accountPage = accountRepository.findAll(pageable);
        List<Account> accounts = accountPage.getContent();
        List<AccountVo> accountVos = new ArrayList<>();
        for (Account account : accounts) {
            AccountVo accountVo = new AccountVo();
            BeanUtils.copyProperties(account, accountVo);
            accountVo.setCreateDateStr(getStringDateFormat(account.getCreateDate(), DATE_ALL_FORMAT));
            accountVo.setUpdateDateStr(getStringDateFormat(account.getUpdateDate(), DATE_ALL_FORMAT));
            accountVos.add(accountVo);
        }
        model.addAttribute("accountList", accountVos);
        model.addAttribute("totalPages", accountPage.getTotalPages());
        model.addAttribute("totalItems", accountPage.getTotalElements());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("size", accountPage.getSize());
        return "main";
    }

    @GetMapping("animalList")
    public String animalList(Integer currentPage, Model model) {
        Pageable pageable = PageRequest.of(currentPage - 1, 10, Sort.Direction.DESC, "updateDate");
        Page<Animal> animalPage = animalRepository.findAll(pageable);
        List<Animal> animals = animalPage.getContent();
        List<AnimalVo> animalVos = new ArrayList<>();
        for (Animal animal : animals) {
            AnimalVo animalVo = new AnimalVo();
            animalVo.setId(animal.getId());
            animalVo.setAnimalName(animal.getAnimalName());
            animalVo.setCreateDate(getStringDateFormat(animal.getCreateDate(), DATE_ALL_FORMAT));
            animalVo.setUpdateDate(getStringDateFormat(animal.getUpdateDate(), DATE_ALL_FORMAT));
            animalVos.add(animalVo);
        }
        model.addAttribute("animalList", animalVos);
        model.addAttribute("totalPages", animalPage.getTotalPages());
        model.addAttribute("totalItems", animalPage.getTotalElements());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("size", animalPage.getSize());
        return "animal";
    }

    @GetMapping("relationList")
    public String relationList(Integer currentPage, Integer accountId, Integer animalId, Model model) {
        Pageable pageable = PageRequest.of(currentPage - 1, 10, Sort.Direction.DESC, "updateDate");
        Page<Relation> relationPage = null;
        if (Objects.nonNull(accountId)){
            model.addAttribute("accountId", accountId);
            relationPage = relationRepository.findRelationByAccountId(pageable, accountId);
        }else if (Objects.nonNull(animalId)){
            model.addAttribute("animalId", animalId);
            relationPage = relationRepository.findRelationByAnimalId(pageable, animalId);
        }else {
            relationPage = relationRepository.findAll(pageable);
        }
        List<Relation> relations = relationPage.getContent();
        List<RelationVo> relationVos = new ArrayList<>();
        for (Relation relation : relations) {
            RelationVo relationVo = new RelationVo();
            Account account = accountRepository.getById(relation.getAccountId());
            Animal animal = animalRepository.getById(relation.getAnimalId());
            relationVo.setId(relation.getId());
            relationVo.setAnimalName(animal.getAnimalName());
            relationVo.setAccount(account.getAccount());
            relationVo.setAccountId(relation.getAccountId());
            relationVo.setAnimalId(relation.getAnimalId());
            relationVo.setStartDateStr(getStringDateFormat(relation.getStartDate(), DATE_FORMAT));
            relationVo.setEndDateStr(getStringDateFormat(relation.getEndDate(), DATE_FORMAT));
            relationVo.setCreateDateStr(getStringDateFormat(relation.getCreateDate(), DATE_ALL_FORMAT));
            relationVo.setUpdateDateStr(getStringDateFormat(relation.getUpdateDate(), DATE_ALL_FORMAT));
            relationVos.add(relationVo);
        }
        model.addAttribute("relationList", relationVos);
        model.addAttribute("totalPages", relationPage.getTotalPages());
        model.addAttribute("totalItems", relationPage.getTotalElements());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("size", relationPage.getSize());
        return "relation";
    }

    @GetMapping("addAccountPage")
    public String addAccountPage() {
        return "addAccountPage";
    }

    @GetMapping("addAnimalPage")
    public String addAnimalPage() {
        return "addAnimalPage";
    }

    @GetMapping("addRelationPage")
    public String addRelationPage(Model model) {
        model.addAttribute("accountDoList", accountRepository.findAll());
        model.addAttribute("relation", new Relation());
        model.addAttribute("animalDoList", animalRepository.findAll());
        return "addRelationPage";
    }

    @PostMapping("addOrEditRelation")
    public String addOrEditRelation(RelationParam relationParam, Model model) {
        List<Relation> relations = relationRepository.findRelationByAccountIdAndAnimalId(relationParam.getAccountId(), relationParam.getAnimalId());
        if (CollectionUtils.isNotEmpty(relations) && relationParam.getId() == null) {
            model.addAttribute("msg", "授权信息已经存在");
            model.addAttribute("relation", relationParam);
            model.addAttribute("accountDoList", accountRepository.findAll());
            model.addAttribute("animalDoList", animalRepository.findAll());
            return "addRelationPage";
        }
        Relation relation = new Relation();
        if (relationParam.getId() == null) {
            relation.setCreateDate(new Date());
        } else {
            Relation repositoryById = relationRepository.getById(relationParam.getId());
            relation.setId(relationParam.getId());
            relation.setCreateDate(repositoryById.getCreateDate());
        }
        relation.setAccountId(relationParam.getAccountId());
        relation.setAnimalId(relationParam.getAnimalId());
        relation.setStartDate(parseDate(relationParam.getStartDate(), DATE_FORMAT));
        if (relationParam.getDays() != null){
            Date date = addDays(parseDate(relationParam.getEndDate(), DATE_FORMAT), relationParam.getDays());
            relation.setEndDate(date);
        }else {
            relation.setEndDate(parseDate(relationParam.getEndDate(), DATE_FORMAT));
        }
        relation.setUpdateDate(new Date());
        relationRepository.saveAndFlush(relation);
        return "redirect:/manage/relationList?currentPage=1";

    }

    @PostMapping("addOrEditAnimal")
    public String addOrEditAnimal(AnimalParam animalParam, Model model) {
        List<Animal> animalByAnimalNames = animalRepository.findAnimalByAnimalName(animalParam.getAnimalName());
        if (CollectionUtils.isNotEmpty(animalByAnimalNames)) {
            model.addAttribute("msg", "名字已经存在");
            model.addAttribute("animal", animalParam);
            if (animalParam.getId() == null) {
                return "addAnimalPage";
            } else {
                return "editAnimalPage";
            }
        }
        Animal animal = new Animal();
        if (animalParam.getId() == null) {
            animal.setCreateDate(new Date());
            animal.setIsValid(1);
        } else {
            Animal byId = animalRepository.getById(animalParam.getId());
            animal.setId(animalParam.getId());
            animal.setCreateDate(byId.getCreateDate());
        }
        animal.setUpdateDate(new Date());
        animal.setAnimalName(animalParam.getAnimalName());
        animalRepository.saveAndFlush(animal);
        return "redirect:/manage/animalList?currentPage=1";
    }

    @PostMapping("addOrEditAccount")
    public String addOrEditAccount(AccountParam accountParam, Model model) {
        List<Account> accountByAccounts = accountRepository.findAccountByAccount(accountParam.getAccount());
        if (CollectionUtils.isNotEmpty(accountByAccounts) && accountParam.getId() == null) {
            model.addAttribute("msg", "账号已经存在");
            model.addAttribute("account", accountParam);
            return "addAccountPage";
        }
        Account account = new Account();
        BeanUtils.copyProperties(accountParam, account);
        if (accountParam.getId() == null) {
            account.setCreateDate(new Date());
            account.setIsValid(1);
        }else {
            Account accountOld = accountRepository.getById(accountParam.getId());
            account.setCreateDate(accountOld.getCreateDate());
            account.setIsValid(accountOld.getIsValid());
        }
        account.setUpdateDate(new Date());
        accountRepository.saveAndFlush(account);
        return "redirect:/manage/main?currentPage=1";
    }

    @GetMapping("deleteAccount")
    public String deleteAccount(Model model,Integer id) {
        List<Relation> relationByAccountId = relationRepository.findRelationByAccountId(id);
        if (CollectionUtils.isNotEmpty(relationByAccountId)){
            List<Integer> animalIds = relationByAccountId.stream().map(Relation::getAnimalId).collect(Collectors.toList());
            List<Animal> animals = animalRepository.findAllById(animalIds);
            List<String> animalNames = animals.stream().map(Animal::getAnimalName).collect(Collectors.toList());
            String msg = String.format("该账号已经被%s引用，不可以删除",StringUtils.join(animalNames,","));
            model.addAttribute("msg",msg);
            return main(1,model);
        }
        accountRepository.deleteById(id);
        return "redirect:/manage/main?currentPage=1";
    }

    @GetMapping("deleteAnimal")
    public String deleteAnimal(Model model, Integer id) {
        //线查询是否被引用
        List<Relation> relationByAnimalId = relationRepository.findRelationByAnimalId(id);
        if (CollectionUtils.isNotEmpty(relationByAnimalId)){
            List<Integer> accountList = relationByAnimalId.stream().map(Relation::getAccountId).collect(Collectors.toList());
            List<Account> accounts = accountRepository.findAllById(accountList);
            List<String> accountNames = accounts.stream().map(Account::getAccount).collect(Collectors.toList());
            String msg = String.format("该动物已经被%s引用，不可以删除",StringUtils.join(accountNames,","));
            model.addAttribute("msg",msg);
            return animalList(1,model);
        }
        animalRepository.deleteById(id);
        return "redirect:/manage/animalList?currentPage=1";
    }

    @GetMapping("deleteRelation")
    public String deleteRelation(Integer id) {
        relationRepository.deleteById(id);
        return "redirect:/manage/relationList?currentPage=1";
    }


    @GetMapping("editAccountPage")
    public String editAccountPage(Model model, Integer id) {
        Account account = accountRepository.getById(id);
        model.addAttribute("account", account);
        return "editAccountPage";
    }

    @GetMapping("editAnimalPage")
    public String editAnimalPage(Model model, Integer id) {
        Animal animal = animalRepository.getById(id);
        model.addAttribute("animal", animal);
        return "editAnimalPage";
    }

    @GetMapping("editRelationPage")
    public String editRelationPage(Model model, Integer id) {
        Relation relation = relationRepository.getById(id);
        RelationParam relationParam = new RelationParam();
        relationParam.setId(relation.getId());
        relationParam.setAccountId(relation.getAccountId());
        relationParam.setAccount(accountRepository.getById(relation.getAccountId()).getAccount());
        relationParam.setAnimalId(relation.getAnimalId());
        relationParam.setStartDate(getStringDateFormat(relation.getStartDate(), DATE_FORMAT));
        relationParam.setEndDate(getStringDateFormat(relation.getEndDate(), DATE_FORMAT));
        model.addAttribute("relation", relationParam);
        model.addAttribute("accountDoList", accountRepository.findAll());
        model.addAttribute("animalDoList", animalRepository.findAll());
        return "editRelationPage";
    }

    public static String getStringDateFormat(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static Date parseDate(String dateStr, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
        }
        return null;
    }
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }
}
