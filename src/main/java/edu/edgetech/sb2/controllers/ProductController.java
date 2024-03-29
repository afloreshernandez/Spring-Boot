package edu.edgetech.sb2.controllers;

import edu.edgetech.sb2.domain.Product;
import edu.edgetech.sb2.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Edge Tech Academy on 12/2/2016.
 */
@Controller
public class ProductController {

	//	Use a service for when things get complicated more than just doing a findAll or delete.
	//	We could go directly to the Entity Repository object (ProductReposity). It is after all a CrudRepository.
	//	So it is very knowledgeable about accessing a database entity.
	// 	But we have a better separation of concerns
	//	when we split the DB request servicing functions to a separate 'Service' class
	@Autowired		//	@Autowired will request SpringBoot to find the ProductService class and instantiate one for us
					//	and assign (INJECT) the class property with the value. This is Dependency Injection.
					//	our class depends on this service
	private ProductService productService;

	//  RequestMethod.GET is the default. It is optional
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("products", productService.listAllProducts());
		return "products";
	}

	@RequestMapping("product/{id}")
	public String show(@PathVariable Integer id, Model model){
		//@RequestParam String thing,
		if ( productService.getProductById(id) != null ) {
			model.addAttribute("product", productService.getProductById(id));
			return "productshow";
		}
		else {
			return "404";
		}
	}

	@RequestMapping("product/edit/{id}")
	public String edit(@PathVariable Integer id, Model model){
		model.addAttribute("product", productService.getProductById(id));
		return "productform";
	}

	@RequestMapping("product/new")
	public String newProduct(Model model){
		model.addAttribute("product", new Product());
		return "productform";
	}

	@RequestMapping(value = "product", method = RequestMethod.POST)
	public String save(Product product){
		productService.saveProduct(product);
		return "redirect:/product/" + product.getId();
	}

	@RequestMapping("product/delete/{id}")
	public String delete(@PathVariable Integer id){
		productService.deleteProduct(id);
		return "redirect:/products";
	}

	//	These items have been added for the new functionality
	//		to make our app a richer MVC example
	@RequestMapping(value = "/oddProducts", method = RequestMethod.GET)
	public String listOdd(Model model){
		model.addAttribute("products", productService.listOddProducts());
		return "products";
	}

	@RequestMapping(value = "/type/{type}")
	public String byType(@PathVariable String type, Model model){
		model.addAttribute("products", productService.findByType(type));
		return "products";
	}

	@RequestMapping(value = "/product/search", method = RequestMethod.POST)
	public String search(@RequestParam String type, Model model){
		model.addAttribute("products", productService.findByType(type));
		return "products";
	}

}
