package logic;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.http.Part;

import dao.VideoDAO;
import dto.VideoDTO;

public class VideoRecordLogic {

  // DBに登録する動画パス用フォルダ
  private static final String DB_VIDEO_DIR = "/video";

  public boolean recordVideo(Part videoPart, int profileId, String uploadPath) {

    boolean result = false;

    try {
      if (videoPart == null || videoPart.getSize() == 0) {
        return false;
      }

      File uploadDir = new File(uploadPath);
      if (!uploadDir.exists()) {
        uploadDir.mkdirs();
      }

      String fileName = "video_" + profileId + "_" + System.currentTimeMillis() + ".webm";

      String savePath = uploadPath + File.separator + fileName;

      String filePath = DB_VIDEO_DIR + "/" + fileName;

      System.out.println("uploadPath = " + uploadPath);
      System.out.println("savePath = " + savePath);

      videoPart.write(savePath);

      File savedFile = new File(savePath);
      System.out.println("exists = " + savedFile.exists());
      System.out.println("length = " + savedFile.length());

      VideoDTO videoDTO = new VideoDTO();
      videoDTO.setProfileId(profileId);
      videoDTO.setVideoType(1);
      videoDTO.setFilePath(filePath);

      VideoDAO videoDAO = new VideoDAO();
      result = videoDAO.insertVideo(videoDTO);

    } catch (IOException e) {
      e.printStackTrace();
    }

    return result;
  }
}