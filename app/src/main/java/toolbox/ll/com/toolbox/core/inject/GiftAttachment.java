package toolbox.ll.com.toolbox.core.inject;

/**
 * Created by ll on 2018/3/27.
 */

public class GiftAttachment extends CustomAttachment<GiftAttachment.Gift>{
    public class Gift{
        private int id;
        private int num;
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
