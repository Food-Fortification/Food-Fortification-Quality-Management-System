package com.beehyv.fortification.validation;

import com.beehyv.fortification.dto.requestDto.SearchCriteria;
import com.beehyv.fortification.dto.requestDto.SearchListRequest;
import com.beehyv.fortification.manager.CategoryManager;
import com.beehyv.parent.exceptions.CustomException;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.*;


@Aspect
@Component("authorityChecker")
public class AuthorityChecker {
    @Autowired
    private KeycloakInfo keycloakInfo;
    @Autowired
    private CategoryManager categoryManager;

    @Around("@annotation(com.beehyv.fortification.validation.HasAdminCategoryAccess)")
    public Object checkUserCategoryAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        List<Long> categoryIds = new ArrayList<>();
        try {
            List<Object> arguments = Arrays.stream(joinPoint.getArgs()).toList();
            Optional<SearchListRequest> searchListRequestOptional = arguments.stream()
                    .filter(i -> i instanceof SearchListRequest).map(d -> (SearchListRequest) d).findFirst();
            if(searchListRequestOptional.isPresent()) {
                Optional<Object> obj = searchListRequestOptional.get().getSearchCriteriaList().stream().filter(c->c.getKey().equals("categoryIds"))
                        .map(SearchCriteria::getValue).findAny();
                if (obj.isPresent() && obj.get() instanceof List) {
                    try {
                        categoryIds.addAll(((List) obj.get()).stream().map(d -> Long.parseLong(d.toString())).toList());
                    } catch (Exception ignore) {}
                }
            }
        }
        catch (Exception exception) {
            throw new CustomException("Error while checking category authorization: " + exception.getMessage(), HttpStatus.BAD_GATEWAY);
        }
        this.hasAdminCategoryAccess(categoryIds);
        return joinPoint.proceed();
    }


    public boolean hasAdminCategoryAccess(Long categoryId) {
        return hasAdminCategoryAccess(Collections.singletonList(categoryId));
    }
    public boolean hasAdminCategoryAccess(List<Long> categoryIds) {
        List<String> categories = categoryManager.findAllNamesByIndependentBatch(false);
        List<String> rolesList = new ArrayList<>();
        categories.forEach(name -> rolesList.add(name.toUpperCase() + "_SUPERADMIN_MODULE"));
        Set<String> roles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        List<String> superList= roles.stream().filter(rolesList::contains).toList();
        if(superList.isEmpty())
            throw new CustomException("User doesn't have super admin roles", HttpStatus.FORBIDDEN);
        if(categoryIds.stream().anyMatch(d -> {
            String categoryName = categoryManager.findById(d).getName();
            return superList.stream().noneMatch(role -> role.contains(categoryName));
        }))
            throw new CustomException("Not authorized for all the categories requested", HttpStatus.FORBIDDEN);
        return true;
    }

}