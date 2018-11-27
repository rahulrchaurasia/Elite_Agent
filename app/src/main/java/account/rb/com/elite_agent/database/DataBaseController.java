package account.rb.com.elite_agent.database;

import android.content.Context;



import java.util.ArrayList;
import java.util.List;

import account.rb.com.elite_agent.core.model.LoginEntity;
import account.rb.com.elite_agent.core.model.OrderStatusEntity;
import account.rb.com.elite_agent.core.model.UserEntity;
import io.realm.Realm;


/**
 * Created by Rajeev Ranjan on 02/02/2018.
 */

public class DataBaseController {
    Context mContext;
    Realm realm;

    public DataBaseController(Context mContext) {
        this.mContext = mContext;
        realm = Realm.getDefaultInstance();
    }





    // region Login Detail

//    public void logout() {
//        realm.beginTransaction();
//        realm.delete(UserEntity.class);
//        realm.commitTransaction();
//    }
//
//    public UserEntity getUserData() {
//        UserEntity entity = realm.where(UserEntity.class).findFirst();
//        if (entity != null)
//            return entity;
//        else
//            return null;
//    }

    public ArrayList<String> getOrderStatusList() {
        List<OrderStatusEntity> categoryEntityList = realm.where(OrderStatusEntity.class).findAll();
        ArrayList categoryList = new ArrayList();
        for (int i = 0; i < categoryEntityList.size(); i++) {
            //listCity.add(list_Make.get(i).getRTO_CodeDiscription());
            //listCity.add(list_Make.get(i).getRTO_City());
            categoryList.add(categoryEntityList.get(i).getOrder_status());
        }
        return categoryList;
    }

    public int getOrderStattusId(String categoryName) {
        OrderStatusEntity entity = realm.where(OrderStatusEntity.class)
                .equalTo("order_status", categoryName).findFirst();
        if (entity != null)
            return entity.getId();
        else
            return 0;
    }

    //endregion


}


