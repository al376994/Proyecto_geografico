package Geografico.controller;

import Geografico.model.API.APISportsData;
import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value="/deportes")
public class DeporteController {

    private final DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
    private final APISportsData APISportsData = new APISportsData();

}
