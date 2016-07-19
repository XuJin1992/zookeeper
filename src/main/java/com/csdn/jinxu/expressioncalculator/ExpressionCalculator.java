package com.csdn.jinxu.expressioncalculator;


import com.google.common.collect.Maps;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;


/**
 * 实现描述：目前只支持非科学计数法的加、减、乘、除、小括号运算，可以正负数，但是暂时不能处理形如"(..(+)..)或者(..(-)..)"
 *
 * @author jin.xu
 * @version v1.0.0
 * @see
 * @since 15-11-7 下午1:39
 */
public class ExpressionCalculator {
    /**
     * ---------------运算符优先级表-------------
     * 运算符优先级表--行:栈顶peek;列:当前current;
     * f:current优先级小于peek
     * t:current优先级大于peek
     * *:同时抵消
     * &:不存在情况
     * $:匹配结束
     * []] +  -  *  /  (  )  #
     * +   f  f  t  t  t  f  f
     * -   f  f  t  t  t  f  f
     * *   f  f  t  t  t  f  f
     * /   f  f  t  t  t  f  f
     * (   t  t  t  t  t  e  &
     * )   &  &  &  &  &  &  &
     * #   t  t  t  t  t  &  e
     * -----------------------------------------
     */
    private static Character[] chars = new Character[] { '+', '-', '*', '/', '(', ')', '#' };
    private static Character[][] operatorPriorityMap = new Character[chars.length][chars.length];
    private static Map<Character, Integer> charPositionMap = Maps.newHashMap();

    static {
        operatorPriorityMap[0][0] = 'f';
        operatorPriorityMap[0][1] = 'f';
        operatorPriorityMap[0][2] = 't';
        operatorPriorityMap[0][3] = 't';
        operatorPriorityMap[0][4] = 't';
        operatorPriorityMap[0][5] = 'f';
        operatorPriorityMap[0][6] = 'f';
        operatorPriorityMap[1][0] = 'f';
        operatorPriorityMap[1][1] = 'f';
        operatorPriorityMap[1][2] = 't';
        operatorPriorityMap[1][3] = 't';
        operatorPriorityMap[1][4] = 't';
        operatorPriorityMap[1][5] = 'f';
        operatorPriorityMap[1][6] = 'f';
        operatorPriorityMap[2][0] = 'f';
        operatorPriorityMap[2][1] = 'f';
        operatorPriorityMap[2][2] = 't';
        operatorPriorityMap[2][3] = 't';
        operatorPriorityMap[2][4] = 't';
        operatorPriorityMap[2][5] = 'f';
        operatorPriorityMap[2][6] = 'f';
        operatorPriorityMap[3][0] = 'f';
        operatorPriorityMap[3][1] = 'f';
        operatorPriorityMap[3][2] = 't';
        operatorPriorityMap[3][3] = 't';
        operatorPriorityMap[3][4] = 't';
        operatorPriorityMap[3][5] = 'f';
        operatorPriorityMap[3][6] = 'f';
        operatorPriorityMap[4][0] = 't';
        operatorPriorityMap[4][1] = 't';
        operatorPriorityMap[4][2] = 't';
        operatorPriorityMap[4][3] = 't';
        operatorPriorityMap[4][4] = 't';
        operatorPriorityMap[4][5] = 'e';
        operatorPriorityMap[4][6] = '&';
        operatorPriorityMap[5][0] = '&';
        operatorPriorityMap[5][1] = '&';
        operatorPriorityMap[5][2] = '&';
        operatorPriorityMap[5][3] = '&';
        operatorPriorityMap[5][4] = '&';
        operatorPriorityMap[5][5] = '&';
        operatorPriorityMap[5][6] = '&';
        operatorPriorityMap[6][0] = 't';
        operatorPriorityMap[6][1] = 't';
        operatorPriorityMap[6][2] = 't';
        operatorPriorityMap[6][3] = 't';
        operatorPriorityMap[6][4] = 't';
        operatorPriorityMap[6][5] = '&';
        operatorPriorityMap[6][6] = 'e';

        charPositionMap.put('+', 0);
        charPositionMap.put('-', 1);
        charPositionMap.put('*', 2);
        charPositionMap.put('/', 3);
        charPositionMap.put('(', 4);
        charPositionMap.put(')', 5);
        charPositionMap.put('#', 6);
    }

    private ExpressionCalculator() {
    }

    /**
     * 表达式计算
     *
     * @param expression 表达式
     * @return
     */
    public static String calculate(String expression) {

        //预处理
        String previousExpression=previousProcess(expression);

        //1、中缀表达式转后缀表达式
        Stack<String> suffixStack = transfer(previousExpression);
        Collections.reverse(suffixStack);

        //2、计算后缀表达式
        Stack<String> resultStack = new Stack<String>();
        String firstValue, secondValue, currentValue;
        while (!suffixStack.isEmpty()) {
            currentValue = suffixStack.pop();
            if (!isOperator(currentValue.charAt(0))) {
                resultStack.push(currentValue);
            } else {
                secondValue = resultStack.pop();
                firstValue = resultStack.pop();
                String tempResult = calculate(firstValue, secondValue, currentValue.charAt(0));
                resultStack.push(tempResult);
            }
        }

        return resultStack.pop();
    }

    /**
     * 预处理：1、正负号处理
     * @param expression 表达式
     * @return
     */
    private static String previousProcess(String expression) {
        char []result=expression.toCharArray();

        //TODO ((+))3待处理
        for(int i=0;i<result.length;++i) {
           if ((0 < i &&'('==result[i-1])&&('-' == result[i]|| '+' ==  result[i])&&( i<result.length-1 &&')'==result[i+1])) {
               throw new IllegalArgumentException("表达式不符合规范,包含'"+result[i-1]+result[i]+result[i+1]+"'");
           }
        }

        //2、去掉()里的-、+号，如-123=0-123, (-123)=(0-123)
        StringBuilder retValue2=new StringBuilder();
        for(int i=0;i<result.length;++i) {
            if (isSignOperator(i, result)) {
                retValue2.append('0');
            }
            retValue2.append(result[i]);
        }

        return retValue2.toString();
    }

    /**
     * 判断是否是正负号
     * @param index 下标
     * @param result 字符串
     * @return
     */
    private static boolean isSignOperator(int index,char []result) {
        if(index<result.length) {
            //-、+号在最前面，一定是运算符 如:-2+3
            if (0 == index && ('-' == result[index] || '+' == result[index])) {
                return true;
            }
            //-、+号在左括号右边，一定是运算符,如：2-(-2)
            else if (0 != index &&'('==result[index-1]&&('-' == result[index]|| '+' ==  result[index])) {
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    /**
     * 中缀表达式转后缀表达式
     *
     * @param expression 中缀表达式
     * @return
     */
    private static Stack<String> transfer(String expression) {
        Stack<String> suffixStack = new Stack<String>();//后缀式栈
        Stack<Character> operateStack = new Stack<Character>();//运算符栈
        operateStack.push('#');
        expression += '#';

        char[] arr = expression.toCharArray();
        char currentChar;
        int currentIndex = 0;
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            currentChar = arr[i];
            if (isOperator(currentChar)) {
                if (count > 0) {
                    suffixStack.push(new String(arr, currentIndex, count));
                    count = 0;
                }
                boolean isEqual = false;
                while (compareTo(operateStack.peek(), currentChar) >= 0) {
                    if (0 == compareTo(operateStack.peek(), currentChar)) {
                        operateStack.pop();
                        isEqual = true;
                        break;
                    }
                    suffixStack.push(String.valueOf(operateStack.pop()));
                }
                if (!isEqual) {
                    operateStack.push(currentChar);
                }
                currentIndex = i + 1;
            } else {
                ++count;
            }
        }
        return suffixStack;
    }

    /**
     * 判断是否是运算符符号
     *
     * @param ch 字符
     * @return
     */
    private static boolean isOperator(char ch) {
        return '+' == ch || '-' == ch || '*' == ch || '/' == ch || '(' == ch || ')' == ch || '#' == ch;
    }

    /**
     * 利用映射表计算优先级
     *
     * @param currentChar 当前字符串
     * @param peekChar    栈顶元素
     * @return
     */
    private static Integer compareTo(char peekChar, char currentChar) {
        Integer result;
        if (operatorPriorityMap[charPositionMap.get(peekChar)][charPositionMap.get(currentChar)].equals('t')) {
            result = -1;
        } else if (operatorPriorityMap[charPositionMap.get(peekChar)][charPositionMap.get(currentChar)].equals('f')) {
            result = 1;
        } else if (operatorPriorityMap[charPositionMap.get(peekChar)][charPositionMap.get(currentChar)].equals('e')) {
            result = 0;
        } else {
            throw new IllegalArgumentException("表达式错误");
        }
        return result;
    }

    /**
     * 给定的运算符运算
     *
     * @param firstValue  第一个计算数
     * @param secondValue 第二个计算数
     * @param currentChar 运算符
     * @return
     */
    private static String calculate(String firstValue, String secondValue, char currentChar) {
        String result = "";
        switch (currentChar) {
        case '+':
            result = String.valueOf(HighPrecisionAgorithm.add(firstValue, secondValue));
            break;
        case '-':
            result = String.valueOf(HighPrecisionAgorithm.sub(firstValue, secondValue));
            break;
        case '*':
            result = String.valueOf(HighPrecisionAgorithm.mul(firstValue, secondValue));
            break;
        case '/':
            result = String.valueOf(HighPrecisionAgorithm.div(firstValue, secondValue));
            break;
        }
        return result;
    }

    /**
     * 支持高精度计算
     */
    private static class HighPrecisionAgorithm {
        private static final int DEF_DIV_SCALE = 16;

        private HighPrecisionAgorithm() {
        }

        /**
         * 提供精确的加法运算
         *
         * @param value1 被加数
         * @param value2 加数
         * @return 两个参数的和
         */
        public static BigDecimal add(BigDecimal value1, BigDecimal value2) {
            return value1.add(value2);
        }

        public static BigDecimal add(String value1, String value2) {
            BigDecimal temp1 = new BigDecimal(value1);
            BigDecimal temp2 = new BigDecimal(value2);
            return temp1.add(temp2);
        }

        /**
         * 提供精确的减法运算
         *
         * @param value1 被减数
         * @param value2 减数
         * @return 两个参数的差
         */
        public static BigDecimal sub(BigDecimal value1, BigDecimal value2) {
            return value1.subtract(value2);
        }

        public static BigDecimal sub(String value1, String value2) {
            BigDecimal temp1 = new BigDecimal(value1);
            BigDecimal temp2 = new BigDecimal(value2);
            return temp1.subtract(temp2);
        }

        /**
         * 提供精确的乘法运算
         *
         * @param value1 被乘数
         * @param value2 乘数
         * @return 两个参数的积
         */
        public static BigDecimal mul(BigDecimal value1, BigDecimal value2) {
            return value1.multiply(value2);
        }

        public static BigDecimal mul(String value1, String value2) {
            BigDecimal temp1 = new BigDecimal(value1);
            BigDecimal temp2 = new BigDecimal(value2);
            return temp1.multiply(temp2);
        }

        /**
         * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入
         *
         * @param value1 被除数
         * @param value2 除数
         * @return 两个参数的商
         */
        public static BigDecimal div(BigDecimal value1, BigDecimal value2) {
            return div(value1, value2, DEF_DIV_SCALE);
        }

        public static BigDecimal div(String value1, String value2) {
            return div(value1, value2, DEF_DIV_SCALE);
        }

        /**
         * 提供精确的除法运算
         *
         * @param value1 被除数
         * @param value2 除数
         * @param scale  表示表示需要精确到小数点以后几位
         * @return 两个参数的商
         */
        public static BigDecimal div(BigDecimal value1, BigDecimal value2, int scale) {
            if (scale < 0) {
                throw new IllegalArgumentException("精度scale必须是整数");
            }
            return value1.divide(value2, scale, java.math.BigDecimal.ROUND_HALF_UP);
        }
        public static BigDecimal div(String value1, String value2, int scale) {
            if (scale < 0) {
                throw new IllegalArgumentException("精度scale必须是整数");
            }
            BigDecimal temp1 = new BigDecimal(value1);
            BigDecimal temp2 = new BigDecimal(value2);
            return temp1.divide(temp2, scale, BigDecimal.ROUND_HALF_UP);
        }

        /**
         * 提供精确的小数位四舍五入处理
         *
         * @param value 需要四舍五入的数字
         * @param scale 小数点后保留几位
         * @return 四舍五入后的结果
         */
        public static BigDecimal round(BigDecimal value, int scale) {
            if (scale < 0) {
                throw new IllegalArgumentException("精度scale必须是整数");
            }
            return value.setScale(scale, BigDecimal.ROUND_HALF_UP);
        }

        public static BigDecimal round(String value, int scale) {
            if (scale < 0) {
                throw new IllegalArgumentException("精度scale必须是整数");
            }
            BigDecimal temp = new BigDecimal(value);
            return temp.setScale(scale, BigDecimal.ROUND_HALF_UP);
        }
    }
}

