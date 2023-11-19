package com.filmweb.controller;

import com.filmweb.entity.Video;
import com.filmweb.util.JPAUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@ApplicationScoped
@Controller
@Path("/")
public class HomeController {

    @Inject
    private JPAUtil jpaUtil;

    @GET
    @Path("home")
    public String index(){
        EntityManager entityManager = jpaUtil.getEntityManager();
        entityManager.getTransaction().begin();
        Video video = new Video();
        video.setHeading("test data");
        entityManager.persist(video);
        entityManager.getTransaction().commit();
        return "home.jsp";
    }
}
