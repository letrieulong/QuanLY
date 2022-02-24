package com.example.appsp;

public class UrlServer {
    public static final String localhost= "192.168.1.6";

    public static final String loaisanpham = "http://" + localhost + "/sanpham/loaisp.php";
    public static final String urlquan = "http://" + localhost + "/sanpham/getloaisp.php?page=";
    public static final String newsp = "http://" + localhost + "/sanpham/newsp.php";
    public static final String donhang = "http://" + localhost + "/sanpham/inforkhachhang.php";
    public static final String chitiet = "http://" + localhost + "/sanpham/chitiet.php";
    public static final String listdonhang = "http://" + localhost + "/sanpham/danhsachdonhang.php";
    public static final String listmoresp = "http://" + localhost + "/sanpham/allmoresp.php";
    public static final String login = "http://" + localhost + "/sanpham/taikhoan/login.php";
    public static final String inforuser = "http://" + localhost + "/sanpham/taikhoan/datauser.php/?name=";
    public static final String register = "http://" + localhost + "/sanpham/taikhoan/register.php";
    public static final String updateprofile = "http://" + localhost + "/sanpham/taikhoan/update.php";
    public static final String delete = "http://" + localhost + "/sanpham/deletesp.php";
    public static final String insert = "http://" + localhost + "/sanpham/upload.php";
    public static final String update = "http://" + localhost + "/sanpham/update.php";
}
