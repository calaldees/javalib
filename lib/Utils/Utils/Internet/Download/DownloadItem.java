package Utils.Internet.Download;

public interface DownloadItem {
  public void setDestination(String destination) throws Exception;
  public boolean isCurentlyDownloading();
  public void startDownload() throws DownloadFailedException;
  public void pauseDownload();
  public void cancelDownload();
  public String getHostName();
  public float percentComplete();
  public int  sizeInBytes();
  public boolean existsOnServer();
}
