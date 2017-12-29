package xmu.crms.web.VO;

/**
 * Demo UploadAvatarVO
 *
 * @author drafting_dreams
 * @date 2017/12/29
 */
public class UploadAvatarVO {
    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public UploadAvatarVO(){}

    public UploadAvatarVO(String fileName) {
        this.file = fileName;
    }
}
