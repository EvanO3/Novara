package com.Novara.Budgeting.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {
    String message;
    boolean success;
    Object data;


    public APIResponse( String message ,boolean success){
        this.message= message;
        this.success=success;
    }
    
  
}
