package xyz.sallai.r1.bean;

public class MusicInfoBean {



        private int duration;
        private String imgUrl;
        private String hdImgUrl;
        private String artist;
        private String lyric;
        private String album;
        private String id;
        private String title;
        private String url;
        public void setDuration(int duration) {
            this.duration = duration;
        }
        public int getDuration() {
            return duration;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
        public String getImgUrl() {
            return imgUrl;
        }

        public void setHdImgUrl(String hdImgUrl) {
            this.hdImgUrl = hdImgUrl;
        }
        public String getHdImgUrl() {
            return hdImgUrl;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }
        public String getArtist() {
            return artist;
        }

        public void setLyric(String lyric) {
            this.lyric = lyric;
        }
        public String getLyric() {
            return lyric;
        }

        public void setAlbum(String album) {
            this.album = album;
        }
        public String getAlbum() {
            return album;
        }

        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setUrl(String url) {
            this.url = url;
        }
        public String getUrl() {
            return url;
        }

    @Override
    public String toString() {
        return "MusicInfoBean{" +
                "duration=" + duration +
                ", imgUrl='" + imgUrl + '\'' +
                ", hdImgUrl='" + hdImgUrl + '\'' +
                ", artist='" + artist + '\'' +
                ", lyric='" + lyric + '\'' +
                ", album='" + album + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
