package com.hav.hav_imobiliaria.security.requestSecurity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupChatRequest {

    private List<Integer> userIds;
    private String chat_name;
    private String chat_image;

}
