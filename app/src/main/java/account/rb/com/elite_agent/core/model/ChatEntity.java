package account.rb.com.elite_agent.core.model;

public  class ChatEntity {
        /**
         * comment_id : 7
         * request_id : 71
         * display_request_id : EL0071
         * comments : Documents Pending
         * comments_by : Admin
         * created_date : 2019-01-09 12:27:17
         */

        private int comment_id;
        private String request_id;
        private String display_request_id;
        private String comments;
        private String comments_by;
        private String created_date;

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

        public String getDisplay_request_id() {
            return display_request_id;
        }

        public void setDisplay_request_id(String display_request_id) {
            this.display_request_id = display_request_id;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getComments_by() {
            return comments_by;
        }

        public void setComments_by(String comments_by) {
            this.comments_by = comments_by;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }
    }