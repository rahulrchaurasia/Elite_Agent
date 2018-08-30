package account.rb.com.elite_agent.core.controller.register;

import android.content.Context;
import android.os.AsyncTask;

import account.rb.com.elite_agent.core.model.LoginEntity;
import account.rb.com.elite_agent.core.model.OrderStatusEntity;
import io.realm.Realm;

/**
 * Created by IN-RB on 07-02-2018.
 */

public class AsyncLoginMaster extends AsyncTask<Void, Void, Void> {

    Context mContext;
    LoginEntity loginEntity;

    public AsyncLoginMaster(Context context, LoginEntity list) {
        this.loginEntity = list;
        mContext = context;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();

            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(OrderStatusEntity.class);
                    realm.copyToRealmOrUpdate(loginEntity.getOrderstatuslist());
                }
            });
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(loginEntity.getUserdetails());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        super.onPostExecute(aVoid);

    }
}
