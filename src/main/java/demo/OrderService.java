package demo;

public class OrderService {
    public static String queryByOrderId(Integer id) throws InterruptedException {
        if(id==2){
            //throw new RuntimeException();
            Thread.sleep(2000);
            return "用户"+id+"延时后查询订单成功";
        }else{
            Thread.sleep(500);
            return "查询订单成功";
        }
    }
}