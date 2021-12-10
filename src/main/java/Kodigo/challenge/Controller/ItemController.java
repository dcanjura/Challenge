package Kodigo.challenge.Controller;

import Kodigo.challenge.Model.Item;
import Kodigo.challenge.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/home")
public class ItemController {

    String home(){
        return "home";
    }

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/item")
    public List<Item> getAllItems(){ return itemRepository.findAll(); }

    @GetMapping("/item/{id}")
    public Optional<Item> getItem(@PathVariable("id") long idItem){
        if(!itemRepository.existsById(idItem)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item with itemId "+ idItem +" does not exists!");
        }
        else{
            return itemRepository.findById(idItem);
        }
    }

    @GetMapping("/item/{status}/{enteredBy}")
    public List<Item> getItemsByStatusNEnteredBy(@PathVariable("status") String status, @PathVariable("enteredBy") String enteredBy){
        return itemRepository.findByItemStatusAndItemEnteredByUser(status, enteredBy);
    }

    @GetMapping("/item/{pageNo}/{pageSize}/{sortByField}")
    public List<Item> getAllItemsPageNSort(@PathVariable("pageNo") Integer pNo, @PathVariable("pageSize") Integer pSize, @PathVariable("sortByField") String sBy){
        Pageable paging = PageRequest.of(pNo, pSize, Sort.by(sBy).descending());
        Page<Item> pagedResult = itemRepository.findAll(paging);
        if(pagedResult.hasContent()){
            return pagedResult.getContent();
        }
        else {
            return new ArrayList<Item>();
        }
    }

    @PostMapping("/item")
    public ResponseEntity<Item> saveItem(@RequestBody Item item) throws ResponseStatusException {
        //Checks if Item with itemId already exists if not, it saves it
        if(itemRepository.existsById(item.getItemId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item with itemId "+ item.getItemId() +" already exists!");
        }
        else{
            itemRepository.save(item);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PutMapping("/item")
    public Item updateItem(@RequestBody Item item){
        //Checks if item exists before updating it
        if(itemRepository.existsById(item.getItemId())){
            itemRepository.save(item);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item with itemId "+ item.getItemId() +" does not exists!");
        }
        return item;
    }

    @DeleteMapping("/item/{id}")
    public void deleteItem(@PathVariable("id") long idItem){
        if(itemRepository.existsById(idItem)){
            itemRepository.deleteById(idItem);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item with itemId "+ idItem +" does not exists!");
        }
    }

    @DeleteMapping("/item")
    public void deleteAllItems(){ itemRepository.deleteAll(); }
}
