package com.poly.asm.controller.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.dao.BrandRepository;
import com.poly.asm.dao.CategoryRepository;
import com.poly.asm.dao.DetailedImageRepository;
import com.poly.asm.dao.ProductRepository;
import com.poly.asm.model.Brand;
import com.poly.asm.model.Category;
import com.poly.asm.model.DetailedImage;
import com.poly.asm.model.Product;
import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/shoeshop/admin/list-product")
public class ProductController {

	@Autowired
	ProductRepository dao; // Làm việc với DAO product

	@Autowired
	CategoryRepository daoCategory; // Làm việc với Category

	@Autowired
	BrandRepository daoBrandRepository; // làm việc với brand

	@Autowired
	DetailedImageRepository daoDetailedImage; // làm việc với ảnh chi tiết

	@Autowired
	SessionService session;

	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") String id, @ModelAttribute("user") User user) {

		if (session.get("user") == null) {
			// Xử lý khi session là null
			// Ví dụ: Tạo một đối tượng User mặc định
			User defaultUser = new User();
			model.addAttribute("user", defaultUser);
		} else {
			user = session.get("user");
			// System.out.println(user.getImage() + "ssssssssssssssssssssssssssss");
			model.addAttribute("user", user);
		}
		// product
		Product product = dao.findById(id).get();
		model.addAttribute("product", product);
		List<Product> products = dao.findAll();
		model.addAttribute("products", products);

//		Danh mục
		Category itemCategory = new Category();
		model.addAttribute("category", itemCategory);
		List<Category> itemsCategories = daoCategory.findAll();
		model.addAttribute("categories", itemsCategories);
// Nhãn hàng
		Brand itemBrand = new Brand();
		model.addAttribute("brand", itemBrand);
		List<Brand> itemsBrand = daoBrandRepository.findAll();
		model.addAttribute("brands", itemsBrand);

//		ảnh chi tiết
		DetailedImage detailedImage = new DetailedImage();
		model.addAttribute("detailedImage", detailedImage);
		List<DetailedImage> listDetailedImages = daoDetailedImage.findAll();
		model.addAttribute("DetailedImages", listDetailedImages);
		for (DetailedImage d : listDetailedImages) {
			if (d.getProduct() != null && d.getProduct().getId().equalsIgnoreCase(product.getId())) {
				model.addAttribute("mainImage", d.getMainImage());
//				model.addAttribute("image1", d.getDetailedOne());
//				model.addAttribute("image2", d.getDetailedTwo());
//				model.addAttribute("image3", d.getDetailedThree());
//				model.addAttribute("idProduct", d.getProduct().getId());
//				model.addAttribute("idImage", d.getId_image());
				model.addAttribute("detailedImage", d);
			}
		}

		return "/admin/views/ui-product";

	}

	@RequestMapping("/create")
	public String create(Model model, @Valid @ModelAttribute("product") Product product, BindingResult rs,
			@ModelAttribute("detailedImage") @Valid DetailedImage detailedImage, @ModelAttribute("user") User user) {
		if (session.get("user") == null) {
			// Xử lý khi session là null
			// Ví dụ: Tạo một đối tượng User mặc định
			User defaultUser = new User();
			model.addAttribute("user", defaultUser);
		} else {
			user = session.get("user");
			// System.out.println(user.getImage() + "ssssssssssssssssssssssssssss");
			model.addAttribute("user", user);
		}
//		Danh mục
		Category itemCategory = new Category();
		model.addAttribute("category", itemCategory);
		List<Category> itemsCategories = daoCategory.findAll();
		model.addAttribute("categories", itemsCategories);
// Nhãn hàng
		Brand itemBrand = new Brand();
		model.addAttribute("brand", itemBrand);
		List<Brand> itemsBrand = daoBrandRepository.findAll();
		model.addAttribute("brands", itemsBrand);
		if (rs.hasErrors()) {
			String successMessage = "Create failed";
			model.addAttribute("failed", successMessage);
			return "/admin/views/ui-product";
		}
		dao.save(product);
		String successMessage = "Create successful";
		model.addAttribute("successMessage", successMessage);
		return "redirect:/shoeshop/admin/list-product/edit-update/" + product.getId();
	}

	@RequestMapping("/update")
	public String update(Model model, @Valid @ModelAttribute("product") Product product, BindingResult rs,
			@Valid @ModelAttribute("detailedImage") DetailedImage detailedImage, @ModelAttribute("user") User user) {
		if (session.get("user") == null) {
			// Xử lý khi session là null
			// Ví dụ: Tạo một đối tượng User mặc định
			User defaultUser = new User();
			model.addAttribute("user", defaultUser);
		} else {
			user = session.get("user");
			// System.out.println(user.getImage() + "ssssssssssssssssssssssssssss");
			model.addAttribute("user", user);
		}
//		Danh mục
		Category itemCategory = new Category();
		model.addAttribute("category", itemCategory);
		List<Category> itemsCategories = daoCategory.findAll();
		model.addAttribute("categories", itemsCategories);
// Nhãn hàng
		Brand itemBrand = new Brand();
		model.addAttribute("brand", itemBrand);
		List<Brand> itemsBrand = daoBrandRepository.findAll();
		model.addAttribute("brands", itemsBrand);
		if (rs.hasErrors()) {
			String successMessage = "Update failed";
			model.addAttribute("Updatefailed", successMessage);
			return "/admin/views/ui-product";
		}

		dao.save(product);
		return "redirect:/shoeshop/admin/list-product/edit-update/" + product.getId();
	}

	@RequestMapping("/edit-update/{id}")
	public String editUpdate(Model model, @PathVariable("id") String id, @ModelAttribute("user") User user) {
		if (session.get("user") == null) {
			// Xử lý khi session là null
			// Ví dụ: Tạo một đối tượng User mặc định
			User defaultUser = new User();
			model.addAttribute("user", defaultUser);
		} else {
			user = session.get("user");
			// System.out.println(user.getImage() + "ssssssssssssssssssssssssssss");
			model.addAttribute("user", user);
		}
		String successMessage = "Successful";
		model.addAttribute("successMessage", successMessage);
		// product
		Product product = dao.findById(id).get();
		model.addAttribute("product", product);
		List<Product> products = dao.findAll();
		model.addAttribute("products", products);

//		Danh mục
		Category itemCategory = new Category();
		model.addAttribute("category", itemCategory);
		List<Category> itemsCategories = daoCategory.findAll();
		model.addAttribute("categories", itemsCategories);
// Nhãn hàng
		Brand itemBrand = new Brand();
		model.addAttribute("brand", itemBrand);
		List<Brand> itemsBrand = daoBrandRepository.findAll();
		model.addAttribute("brands", itemsBrand);

//		ảnh chi tiết
		DetailedImage detailedImage = new DetailedImage();
		model.addAttribute("detailedImage", detailedImage);
		List<DetailedImage> listDetailedImages = daoDetailedImage.findAll();
		model.addAttribute("DetailedImages", listDetailedImages);
		for (DetailedImage d : listDetailedImages) {
			if (d.getProduct() != null && d.getProduct().getId().equalsIgnoreCase(product.getId())) {
				model.addAttribute("mainImage", d.getMainImage());
//				model.addAttribute("image1", d.getDetailedOne());
//				model.addAttribute("image2", d.getDetailedTwo());
//				model.addAttribute("image3", d.getDetailedThree());
//				model.addAttribute("idProduct", d.getProduct().getId());
//				model.addAttribute("idImage", d.getId_image());
				model.addAttribute("detailedImage", d);
			}
		}

		return "/admin/views/ui-product";

	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) {
		dao.deleteById(id);
		return "redirect:/shoeshop/admin/list-product";
	}
}
