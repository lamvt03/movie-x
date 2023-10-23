package com.filmweb.controller;

import com.filmweb.entity.Video;
import com.filmweb.util.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/")
public class HomeController extends HttpServlet {

    @Inject
    private JPAUtil jpaUtil;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager entityManager = jpaUtil.getEntityManager();
        entityManager.getTransaction().begin();
        Video video = new Video();
        video.setHeading("context and dependency injection");
        entityManager.persist(video);
        entityManager.getTransaction().commit();
        req.getRequestDispatcher("views/index.jsp").forward(req,resp);
    }
}
