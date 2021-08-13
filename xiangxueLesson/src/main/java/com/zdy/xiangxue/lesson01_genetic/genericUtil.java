package com.zdy.xiangxue.lesson01_genetic;

/**
 * 创建日期：2020-04-22 on 17:46
 * 描述：
 * 作者：zhudongyong
 * <p>
 * java5之前没有泛型
 * <p>
 * <p>
 * <p>
 * 最常用的类型参数名称是:
 * E - Element (Java Collections Framework广泛使用)
 * K - Key
 * N - Number
 * T - Type
 * V - Value
 * S,U,V etc. - 2nd, 3rd, 4th types
 * <p>
 * 泛型：一种可修饰于类，接口，方法，变量的代码规范
 * <p>
 * 好处：
 * 1：在代码编译时候就可以进行代码检查，提前暴露代码问题
 * 2：提升代码规范化和通用性
 */
public class genericUtil<T> {

    //多种类型的参数
    static class Pair<K, V> {
        private K k;
        private V v;


        private K getKey() {
            return k;
        }

        private V getValue() {
            return v;
        }

        private Pair(K k, V v) {
            this.k = k;
            this.v = v;
        }
    }

    //泛型接口
    interface student<K, V> {
        K getKey();

        V getValue();
    }

    static class Clazz<String, Integer> implements student<String, Integer> {
        private String name;
        private Integer age;

        Clazz(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String getKey() {
            return name;
        }

        @Override
        public Integer getValue() {
            return age;
        }
    }


    /**
     * 类型擦除  （其实是保留在类的常量池里面）反射可以拿出来
     * 1:泛型类型的擦除 没有上限，擦除后变成object
     * 2:桥接  其实就是强转
     * 3:擦除残留
     * <p>
     * 通配符  java泛型的PECS原则  product extends Consumer super 提升泛型的灵活性  在集合中用的多
     * 1：上限通配符 <? extends T> 限定 就是表示泛型参数类型的上界，说明参数的类型应该是T或者T的子类。 无法进行set  只读  但是通过反射可以使用set
     * 2：无限通配符<?> 非限定 泛型信息类型未知 等价于<? extends Object>  既不能读也不能写
     * 3：下限通配符 <? super T> 限定 表示的则是类型的下界，说明参数的类型应该是T类型的父类，一直到object。无法进行get  只写
     * <p>
     * extends 可用于的返回类型限定，不能用于参数类型限定。  只读
     * super 可用于参数类型限定，不能用于返回类型限定。  只写
     * 希望只取出，不插入，就使用? extends
     * 希望只插入，不取出，就使用? super
     * 希望，即能插入，又能取出，就不要用通配符？
     * <p>
     * 通配符与泛型<T>的关系：通配符让泛型的转型更灵活
     *
     * @param args
     */
    public static void main(String[] args) {
//        Pair<String, Integer> p1 = new Pair<>("小明", 8);
//        System.out.println("key:" + p1.getKey() + " value:" + p1.getValue());
//        Clazz<String, Integer> clazz = new Clazz<>("小朱", 9);
//        System.out.println("name:" + clazz.getKey() + " age:" + clazz.getValue());
//        Boxxx boxxx = new Boxxx();
//        boxxx.setValue("xiao");

        G<? super D> g = new G();
        g.set(new F());
//        g.set(new B());
        System.out.println("g.get=="+g.get());

    }

    public static class A {
    }

    public static class B extends A {
    }

    public static class C extends B {
    }

    public static class D extends C {
    }

    public static class E extends D {
    }

    public static class F extends E {
    }

    public static class G<T>{
        private T value;

        public void set(T value){
            this.value = value;
        }

        public T get(){
            return value;
        }

    }



    /**
     * 虚拟机指令
     *
     * 面试：
     * Q：泛型的原理？泛型的擦除机制？
     * A：jdk5才引入，为了向下兼容，所以java用的是伪泛型机制
     * Q：java编译器具体如何进行擦除泛型？
     * A：1：检测泛型类型，获取目标类型
     *    2
     *    3
     *    4
     * Q：泛型的副作用？
     * A：1：无法使用java的原始数据类型int....  因为泛型会擦除后会编译成object，对象类型object无法转化数据类型int
     *    2：无法使用inscanof
     * Q：泛型在静态方法和静态类的问题？
     * Q：泛型为啥会导致方法冲突？
     * Q：无法创建泛型类的实例？怎么解决？
     * Q：java里面没有泛型数组？
     *  A extends B  擦除后是B
     *  A[] 父亲是 B[] 数组的协变
     *  如果给数组加上泛型
     *  Array<A>[a1,a2,a3] 擦除时会变成Object  不满足数组的协变规则
     *
     *  ========== 但是如果class<A> 的父类不是class<B> list集合和object不存在协变 ======
     *
     *Q：区分
     * Plate
     * Palte<Object>
     * Palte<?>
     * Palte<? extends T>
     * Palte<? super T>
     *
     */
}
