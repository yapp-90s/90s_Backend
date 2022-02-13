package com.yapp.ios2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BooleanDto implements IDto{
    private Boolean result;
    private String msg;

    public static BooleanDto success(){
        return success("Success");
    }

    public static BooleanDto success(String msg){
        return BooleanDto.builder()
                .result(true)
                .msg(msg)
                .build();
    }
    public static BooleanDto fail(){
        return fail("Error");
    }
    public static BooleanDto fail(String msg){
        return BooleanDto.builder()
                .result(false)
                .msg(msg)
                .build();
    }

}
