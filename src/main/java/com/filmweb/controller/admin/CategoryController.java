package com.filmweb.controller.admin;

import com.filmweb.dto.CategoryDto;
import com.filmweb.entity.Category;
import com.filmweb.service.CategoryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Controller
@Path("/admin")
public class CategoryController {

    @Inject
    private CategoryService categoryService;

    @Inject
    private Models models;

    @Inject
    private HttpSession session;

    @GET
    @Path("/categories")
    public String getCategories(){
        List<CategoryDto> categories = categoryService.findAll();
        models.put("categories", categories);
        return "admin/category-list.jsp";
    }

    @GET
    @Path("/category/add")
    public String getAddCategory(){
        return "admin/category-add.jsp";
    }

    @POST
    @Path("/category/add")
    public String postAddCategory(
            @FormParam("name") String name,
            @FormParam("code") String code
    ){
        Category category = categoryService.findBySlug(code);
        if(category == null){
            categoryService.create(name, code);
            session.setAttribute("addCategorySuccess", true);

        }else{
            session.setAttribute("addCategorySuccess", false);
        }
        return "redirect:admin/categories";
    }

    @GET
    @Path("/category/edit/{id}")
    public String getEditCategory(
            @PathParam("id") UUID id
    ){
        Category category = categoryService.findById(id);
        models.put("category", category);
        return "admin/category-edit.jsp";
    }

    @POST
    @Path("/category/edit/{id}")
    public String postEditCategory(
            @PathParam("id") UUID id,
            @FormParam("name") String name,
            
            // TODO: delete
            @FormParam("code") String code
    ){
        categoryService.update(id, name);
        session.setAttribute("updateCategorySuccess", true);
        return "redirect:admin/categories";
    }

    @POST
    @Path("/category/delete")
    public String postDeleteCategory(
            @FormParam("code") String code
    ){
        categoryService.delete(code);
        session.setAttribute("deleteCategorySuccess", true);
        return "redirect:admin/categories";
    }
}
