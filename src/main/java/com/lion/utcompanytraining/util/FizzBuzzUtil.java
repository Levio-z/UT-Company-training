package com.lion.utcompanytraining.util;


public class FizzBuzzUtil {
    /**
     * 打印1-100
     *
     * 被3整除，打印Fizz
     *
     * 被5整除，打印Buzz
     *
     * 被3整除也被5整除，打印FizzBuzz
     *
     * 都不满足，打印原来的数字
     * @param i
     * @return
     */
    public static String compute(Integer i){
        StringBuilder stringBuilder = new StringBuilder();

        if ( i % 3 == 0){
            stringBuilder.append("Fizz");
        }

        if ( i % 5 == 0){
            stringBuilder.append("Buzz");
        }

        if ("".equals(stringBuilder.toString())){
            stringBuilder.append(i);
        }

        return stringBuilder.toString();
    }
//    public static String compute(Integer a){
//        boolean b = a % 3 == 0;
//        boolean b1 = a % 5 == 0;
//        if (b&&b1){
//            return "FizzBuzz";
//        }else if (b) {
//            return "Fizz";
//        } else if (b1) {
//            return "Buzz";
//        } else {
//            return Integer.toString(a);
//        }
//    }
}
