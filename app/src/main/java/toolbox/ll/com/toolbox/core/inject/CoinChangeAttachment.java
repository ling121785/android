package toolbox.ll.com.toolbox.core.inject;

/**
 * Created by ll on 2018/3/27.
 */

public class CoinChangeAttachment extends CustomAttachment<CoinChangeAttachment.RoomCoin>{

    public CoinChangeAttachment() {
    }

    public class RoomCoin{
        public RoomCoin() {
        }


        private int room_coin=0;
        private int live_coin=0;


        public int getRoom_coin() {
            return room_coin;
        }

        public void setRoom_coin(int room_coin) {
            this.room_coin = room_coin;
        }

        public int getLive_coin() {
            return live_coin;
        }

        public void setLive_coin(int live_coin) {
            this.live_coin = live_coin;
        }
    }
}
