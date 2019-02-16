package graphas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller("ClientController")

public class GraphAsClientControler {

	//@RequestMapping(value = "/")
	public String index() {
		return "index.html";
	}

}
