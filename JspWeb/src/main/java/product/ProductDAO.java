package product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import product.ProductDTO;
import util.DatabaseUtil;

import javax.servlet.http.HttpServletRequest;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class ProductDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public ArrayList<ProductDTO> pdList() {
		ArrayList<ProductDTO> pdList = new ArrayList<ProductDTO>();
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM product");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int productId = rs.getInt("productId");
				String productName = rs.getString("productName");
				int companyId = rs.getInt("companyId");
				int price = rs.getInt("price");
				int soldCount = rs.getInt("soldCount");
				String detail = rs.getString("detail");
				String imgUrl_1 = rs.getString("imgUrl_1");
				
				ProductDTO data = new ProductDTO();
				data.setProductId(productId);
				data.setProductName(productName);
				data.setCompanyId(companyId);
				data.setPrice(price);
				data.setSoldCount(soldCount);
				data.setDetail(detail);
				data.setImgUrl_1(imgUrl_1);
				pdList.add(data);
			}
			pstmt.close();
			conn.close();
		} catch(Exception e) {
			System.out.println("productDAO pdList Error");
			e.printStackTrace();
		}
		return pdList;
	}
	
	
	public ProductDTO getProduct(String id) {
		ProductDTO pdDto = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM product where productId = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				pdDto = new ProductDTO();
				pdDto.setProductId(rs.getInt("productId"));
				pdDto.setProductName(rs.getString("productName"));
				pdDto.setCompanyId(rs.getInt("companyId"));
				pdDto.setPrice(rs.getInt("price"));
				pdDto.setSoldCount(rs.getInt("soldCount"));
				pdDto.setDetail(rs.getString("detail"));
				pdDto.setImgUrl_1(rs.getString("imgUrl_1"));
				
			}		
		} catch (Exception e) {
			System.out.println("getProductError" + e);
		} finally {
			try { if(conn != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
			try { if(pstmt != null) pstmt.close(); } catch (Exception e) {e.printStackTrace();}
			try { if(rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();}
		}
		return pdDto;
	}
	
	public boolean insertProduct(HttpServletRequest request) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean b = false;
		try {
			//업로드할 이미지 경로(절대 경로)
			String uploadDir = "C:\\Users\\apem5\\git\\jspWeb\\JspWeb\\WebContent\\images";
			 //MultipartRequest multi=new MultipartRequest(request, savePath, sizeLimit, new DefaultFileRenamePolicy());
            MultipartRequest multi = new MultipartRequest(request, uploadDir, 5*1024*1024, "utf-8", new DefaultFileRenamePolicy());
            conn = DatabaseUtil.getConnection();
            String SQL = "INSERT INTO product (productId, productName, companyId, price, soldCount, detail, imgUrl_1) values (?, ?, ?, ?, ?, ?, ?)";
            
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, multi.getParameter("productId"));
            pstmt.setString(2, multi.getParameter("productName"));
            pstmt.setString(3, multi.getParameter("companyId"));
            pstmt.setString(4, multi.getParameter("price"));
            pstmt.setString(5, multi.getParameter("soldCount"));
            pstmt.setString(6, multi.getParameter("detail"));
            if(multi.getParameter("imgUrl_1") == null) {
            	//이미지가 없을경우 디폴트 이미지 선택
            	pstmt.setString(7, "../images/#");
            } else {
            	pstmt.setString(7, multi.getFilesystemName("imgUrl_1"));
            }
            if (pstmt.executeUpdate() > 0) b = true;
		} catch (Exception e){
			System.out.println("insertProduct Error" + e);
		} finally {
			try { if(conn != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
			try { if(pstmt != null) pstmt.close(); } catch (Exception e) {e.printStackTrace();}
			try { if(rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();}
		}
		return b;
	}
	
	public boolean updateProduct(HttpServletRequest request) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean b = false;
		try {
			//업로드할 이미지 경로(절대 경로)
			String uploadDir = "C:\\Users\\apem5\\git\\jspWeb\\JspWeb\\WebContent\\images";
			 //MultipartRequest multi=new MultipartRequest(request, savePath, sizeLimit, new DefaultFileRenamePolicy());
            MultipartRequest multi = new MultipartRequest(request, uploadDir, 5*1024*1024, "utf-8", new DefaultFileRenamePolicy());
            conn = DatabaseUtil.getConnection();
            
            if (multi.getFilesystemName("img_Url_1") == null) {
            	String SQL = "UPDATE product SET productName=?, companyId=?, price=?, soldCount=?, detail=? where productId=?";
            	pstmt = conn.prepareStatement(SQL);
            	pstmt.setString(1, multi.getParameter("productId"));
            	pstmt.setString(2, multi.getParameter("productName"));
            	pstmt.setString(3, multi.getParameter("companyId"));
            	pstmt.setString(4, multi.getParameter("price"));
            	pstmt.setString(5, multi.getParameter("soldCount"));
            	pstmt.setString(6, multi.getParameter("detail"));
            } else {
            	String SQL = "UPDATE product SET productName=?, companyId=?, price=?, soldCount=?, detail=?, imgUrl_1=? where productId=?";
            	pstmt = conn.prepareStatement(SQL);
            	pstmt.setString(1, multi.getParameter("productId"));
            	pstmt.setString(2, multi.getParameter("productName"));
            	pstmt.setString(3, multi.getParameter("companyId"));
            	pstmt.setString(4, multi.getParameter("price"));
            	pstmt.setString(5, multi.getParameter("soldCount"));
            	pstmt.setString(6, multi.getParameter("detail"));
            	pstmt.setString(7, multi.getFilesystemName("imgUrl_1"));
            }
            if (pstmt.executeUpdate() > 0) b = true;
		} catch (Exception e){
			System.out.println("updateProduct Error" + e);
		} finally {
			try { if(conn != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
			try { if(pstmt != null) pstmt.close(); } catch (Exception e) {e.printStackTrace();}
			try { if(rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();}
		}
		return b;
	}
	
	public boolean deleteProduct(String productId) {
		boolean b = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			String SQL = "DELETE FROM product WHERE productId = ?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, productId);
			if(pstmt.executeUpdate() > 0) b = true;
		} catch (Exception e) {
			System.out.println("deleteProduct Error" + e);
		} finally {
			try { if(conn != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
			try { if(pstmt != null) pstmt.close(); } catch (Exception e) {e.printStackTrace();}
			try { if(rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();}
		}
		return b;
	}
}
