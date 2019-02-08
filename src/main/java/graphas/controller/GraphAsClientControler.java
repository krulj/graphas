package graphas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("ClientController")
@RequestMapping(value = "/")
public class GraphAsClientControler {

	@ResponseBody
	public String index() {
		return "index.html";
	}

}
