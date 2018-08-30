package account.rb.com.elite_agent.core.model;

import io.realm.RealmObject;

public  class OrderStatusEntity extends RealmObject {
            /**
             * id : 1
             * order_status : Pending
             */

            private int id;
            private String order_status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOrder_status() {
                return order_status;
            }

            public void setOrder_status(String order_status) {
                this.order_status = order_status;
            }
        }