package com.csdn.jinxu.expressioncalculator;

/**
 * 实现描述：表达式计算测试
 *
 * @author jin.xu
 * @version v1.0.0
 * @see
 * @since 15-11-7 下午1:39
 */
public class ExpressionCalculatorMainApp {

    public static void main(String... args) throws Exception {
        String s = "1/3+2*1+(1*3)";
        String result  = ExpressionCalculator.calculate(s);
        System.out.println(result);
    }

}

