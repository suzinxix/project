package bee;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Profile<data> {
    @Entity
    public class User {
        @PrimaryKey
        public int user_id;

        public String nickname;
        public int level;
        public int bee_num; // 레벨에 따라 벌 등급 변함 꿀벌 / 호박벌 / 땅벌 / 말벌 / 꼬마장수말벌/ 장수말벌 / 여왕벌
    }
}
