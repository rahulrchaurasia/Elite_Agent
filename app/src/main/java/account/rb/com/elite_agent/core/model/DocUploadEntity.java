package account.rb.com.elite_agent.core.model;

public  class DocUploadEntity {
        /**
         * status : 1
         * path : http://elite.rupeeboss.com/uploads/1/1559128082.jpg?fa789bdc-8201-11e9-9611-02e7c3c8f89e
         * type : jpg
         * id : 10
         */

      
        private int status;
        private String path;
        private String type;
        private int id;

        public int getstatus() {
            return status;
        }

        public void setstatus(int status) {
            this.status = status;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }