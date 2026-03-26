package com.Novara.Budgeting.Exceptions;

import lombok.AllArgsConstructor;


public class ResourceNotFound extends RuntimeException {
    
    public ResourceNotFound(){
        super();
    }
    public ResourceNotFound(String message){
        super(message);
    }


}
