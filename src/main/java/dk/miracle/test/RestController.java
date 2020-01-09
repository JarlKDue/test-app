package dk.miracle.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class RestController {

    public ItemRepository itemRepository;

    @Autowired
    public RestController(
            ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    String addItem(Item item){
        itemRepository.save(item);
        return "redirect:/";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    String updateItem(Item item){
        Long key = item.getKey();
        Item itemToUpdate = itemRepository.findByKey(key);
        itemToUpdate.lastName =item.lastName;
        itemToUpdate.name =item.name;
        itemRepository.save(itemToUpdate);
        return "redirect:/";
    }

    @RequestMapping(value = "/")
    public String getAll(Model model){
        model.addAttribute("items", itemRepository.findAll());
        return "itemList";
    }
}
