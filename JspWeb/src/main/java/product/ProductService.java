package product;

import java.util.List;

import product.ProductDao;
import productDto.CheckoutProdDto;
import productDto.DetailProdRespDto;
import productDto.HeaderBrandDto;
import productDto.IndexDto;
import productDto.InsertReqDto;

public class ProductService {
	private ProductDao productDao;
	
	public ProductService() {
		productDao = new ProductDao();
	}
	
	public List<IndexDto> ��ǰ��ü����(int limitNum) {
		return productDao.findAllWithLimitNum(limitNum);
	}
	
	public List<IndexDto> ��ǰ��ü����() {
		return productDao.findAll();
	}
	
	public int ��ǰ���(InsertReqDto dto) {
		return productDao.insert(dto);
	}
	
	public DetailProdRespDto ��ǰ�󼼺���(int prodNo) {
		return productDao.findById(prodNo);
	}
	
	public List<IndexDto> ��ǰŰ����ã��(String keyword) {
		return productDao.findByKeyword(keyword);
	}
	
	public List<IndexDto> ��ǰȸ���ڵ�ã��(int compNo) {
		return productDao.findByCompNo(compNo);
	}
	
	public List<HeaderBrandDto> ȸ�������Ʈ() {
		return productDao.getAllCompName();
	}
	
	public List<IndexDto> ��ǰ����() {
		return productDao.findAllSortBySoldCount();
	}
	
	public CheckoutProdDto ���Ż�ǰ����(int id) {
		return productDao.findForBuy(id);
	}
	
	public List<CheckoutProdDto> ���Ż�ǰ����(List<Integer> cartList) {
		return productDao.findForBuy(cartList);
	}
	
	public void ���ż�����(int prodId) {
		productDao.updateSoldCount(prodId);
	}
}