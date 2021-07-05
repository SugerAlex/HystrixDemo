package demo;

public class ProductService {
    public static String queryByProductId() throws InterruptedException {
        return Thread.currentThread().getName()+ "用户查询---商品库存---成功";
    }
}
