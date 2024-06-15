package com.filmweb.dto;

public record GoogleUser (
        String id,
        String email,
        Boolean verified_email,
        String name,
        String given_name,
        String family_name,
        String link,
        String picture
){
}
