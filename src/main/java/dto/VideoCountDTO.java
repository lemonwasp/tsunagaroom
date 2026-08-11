package dto;

public class VideoCountDTO {
  // 本日撮影した動画数
  private int todayVideoCount;

  // 残り撮影可能回数
  private int remainingCount;

  // 未読動画があるかどうか 追加
  private boolean isViewed;

  // コンストラクタ
  public VideoCountDTO() {
  }

  // todayVideoCountのgetter
  public int getTodayVideoCount() {
    return todayVideoCount;
  }

  // todayVideoCountのsetter
  public void setTodayVideoCount(int todayVideoCount) {
    this.todayVideoCount = todayVideoCount;
  }

  // remainingCountのgetter
  public int getRemainingCount() {
    return remainingCount;
  }

  // remainingCountのsetter
  public void setRemainingCount(int remainingCount) {
    this.remainingCount = remainingCount;
  }

  // isViewedのgetter 追加
  public boolean isViewed() {
    return isViewed;
  }

  // isViewedのsetter 追加
  public void setViewed(boolean isViewed) {
    this.isViewed = isViewed;
  }

}
