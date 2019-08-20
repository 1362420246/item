package com.telecomyt.item.util;

import com.telecomyt.item.constant.ImageConstant;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author zhoupengbing
 * @packageName com.telecomyt.forum.utils
 * @email zhoupengbing@telecomyt.com.cn
 * @description 缩略图工具类
 * @createTime 2019年07月08日 10:35:00
 * @Version v1.0
 */
@Service
public class ImageUtils {

    /**
     * 生成缩略图
     * @param path 原图路径
     * @return
     */
    public void zoom(String path,String targetDir,String fileName) {
        String [] types = ImageConstant.types;
        for(int i = 0;i < types.length;i++){
            String type = types[i];
        }
        Arrays.asList(ImageConstant.types)
              .forEach(i -> {
                  // 使用源图像文件名创建ImageIcon对象。
                  ImageIcon imgIcon = new ImageIcon(path);
                  // 得到Image对象。
                  Image img = imgIcon.getImage();
                  int width = 0;
                  int height = 0;
                  String dir = targetDir;
                  try{
                      BufferedImage bi = null;
                      switch(i){
                          case "MODULE_ICON":
                              dir = dir + "/" + ImageConstant.MODULE_ICON_PACKAGE_NAME;
                              width = ImageConstant.MODULE_ICON_WIDTH;
                              height = ImageConstant.MODULE_ICON_HEIGHT;
                              break;
                          case "SUGGEST_IMAGE_FIRST":
                              dir = dir + "/" + ImageConstant.SUGGEST_IMAGE_FIRST_PACKAGE_NAME;
                              width = ImageConstant.SUGGEST_IMAGE_FIRST_WIDTH;
                              height = ImageConstant.SUGGEST_IMAGE_FIRST_HEIGHT;
                              break;
                          case "SUGGEST_IMAGE_SECOND":
                              dir = dir + "/" + ImageConstant.SUGGEST_IMAGE_SECOND_PACKAGE_NAME;
                              width = ImageConstant.SUGGEST_IMAGE_SECOND_WIDTH;
                              height = ImageConstant.SUGGEST_IMAGE_SECOND_HEIGHT;
                              break;
                          case "SUGGEST_IMAGE_THIRD":
                              dir = dir + "/" + ImageConstant.SUGGEST_IMAGE_THIRD_PACKAGE_NAME;
                              width = ImageConstant.SUGGEST_IMAGE_THIRD_WIDTH;
                              height = ImageConstant.SUGGEST_IMAGE_THIRD_HEIGHT;
                              break;
                          default:
                              break;

                      }
                      File file = new File(dir);
                      if(!file.exists()){
                          file.mkdir();
                      }
                      zoom(path,dir,fileName,width,height);
                  }catch(Exception e){
                      e.printStackTrace();
                  }
              });
    }

    /***
     * @methodName zoom
     * @description 等比例缩放图片
     * @author zhoupengbing
     * @param  source 源文件地址
     * @param  dir 文件地址
     * @param  fileName 文件名称
     * @param  width  画布宽度
     * @param  height 画布高度
     * @updateTime 2019/7/8 13:54
     * @return void
     * @throws 
     */
    public static void zoom(String source,String dir,String fileName,int width,int height)throws Exception{
        Thumbnails.Builder<File> fileBuilder = Thumbnails.of(source).scale(1.0).outputQuality(1.0);
        BufferedImage bufferedImage = fileBuilder.asBufferedImage();
        int imageHeight = bufferedImage.getHeight();
        int imageWidth = bufferedImage.getWidth();
        double scalWidth = Double.valueOf(width) / imageWidth;
        double scaleHeight = Double.valueOf(height) / imageHeight;
        String path =  dir + "/" +fileName;
        if(imageHeight <= height || imageWidth <= width){
            //画布宽>高
            if(imageHeight <= height && imageWidth <= width){
                if(scalWidth > scaleHeight){
                    //放大
                    Thumbnails.of(source)
                              .scale(scalWidth)
                              .toFile(path);
                }else{
                    Thumbnails.of(source)
                              .scale(scaleHeight)
                              .toFile(path);
                }
                cropping(path,width,height,path);
            }else if(imageHeight > height){
                Thumbnails.of(source)
                          .scale(scalWidth)
                          .toFile(path);
                cropping(path,width,height,path);
            }else{
                Thumbnails.of(source)
                          .scale(scaleHeight)
                          .toFile(path);
                cropping(path,width,height,path);
            }
        } else{
            if(scalWidth > scaleHeight){
                Thumbnails.of(source)
                          .scale(scalWidth)
                          .toFile(path);
            } else{
                Thumbnails.of(source)
                          .scale(scaleHeight)
                          .toFile(path);
            }
            //裁剪
            cropping(path,width,height,path);
        }
    }

    /***
     * @methodName cropping
     * @description 等比例缩放图片
     * @author zhoupengbing
     * @param  source 源文件地址
     * @param  width  宽度
     * @param  height 画布高度
     * @param  target 存储位置
     * @updateTime 2019/7/8 13:54
     * @return void
     * @throws
     */
    public static void cropping(String source,int width,int height,String target)throws Exception{
        Thumbnails.of(source)
                  .sourceRegion(Positions.CENTER, width, height)
                  .size(width, height)
                  .keepAspectRatio(false)
                  .toFile(target);
    }

    /***
     * @methodName zoom
     * @description 图片加水印
     * @author zhoupengbing
     * @param
     * @updateTime 2019/7/18 15:58
     * @return void
     * @throws
     */
    public static void zoom(){
        String imgPath = "C:\\Users\\张博\\Desktop\\圆形.png";
        //压缩图片路径
        String smallImgPath = imgPath.replace(".", "_small.");
        //水印图位置
        File file= new File("C:\\Users\\张博\\Desktop\\用户默认头像.png") ;
        try {
            //watermark(位置，水印图，透明度)
            Thumbnails.of(imgPath)
                      .size(120,120)
                      .watermark(Positions.CENTER, ImageIO.read(file), 0.5f)
                      .outputQuality(0.5f)
                      .toFile(smallImgPath);
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成用户头像信息
     *
     * @methodName generateAvatar
     * @description 生成用户头像信息
     * @author zhoupengbing
     * @param name 用户姓名
     * @param imageName 头像名称
     * @updateTime 2019/7/18 16:35
     * @return void
     * @throws
     */
    public void generateAvatar(String name,String imageName)throws Exception{
        BufferedImage image = AvatarUtils.generateImg(name, 120, 120);
        ImageIO.write(image,"png",new File("C:\\Users\\qbk\\Desktop\\头像缩略图\\头像\\"+imageName+".png"));
    }

    /**
     *  生成306*306的缩略图
     * @param path 原图路径
     * @param dir 缩略图保存地址
     * @param fileName 生成的缩略图的名称
     */
    public static void zoomFixedSize (String path ,String dir, String fileName) throws Exception {
        zoom(path,dir,fileName,ImageConstant.SUGGEST_IMAGE_THIRD_WIDTH,ImageConstant.SUGGEST_IMAGE_THIRD_HEIGHT);
    }

    public static void main(String[] args) throws Exception{

        ImageUtils imageUtils = new ImageUtils();
//        //生成头像
//        imageUtils.generateAvatar("曲博卡","qbk");
//        imageUtils.generateAvatar("张凯","zk");
//        imageUtils.generateAvatar("江滨","jb");
//        imageUtils.generateAvatar("郭钊","gz");
//        imageUtils.generateAvatar("张盛飞","zsf");




        String path = "C:\\Users\\qbk\\Desktop\\mm.jpg";
        String substring = path.substring(path.lastIndexOf(".")+1);
        System.out.println(substring);

        String targetDir = "C:\\Users\\qbk\\Desktop\\";
        //缩略图
         zoomFixedSize(path,targetDir,"m" + ".png" );



    }
}