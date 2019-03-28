package net.demo.ignite.rest;

import net.demo.ignite.service.CachableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cache/")
public class CachingController {
    @Autowired
    private CachableService cachableService;

    @GetMapping("put/{keynum}/{val}")
    public String putToCache(@PathVariable Long keynum, @PathVariable String val) {
        cachableService.putValueIntoCache(keynum, val);
        return "Value in cache";
    }

    @GetMapping("get/{keynum}")
    public String getFromCache(@PathVariable Long keynum) {
        return cachableService.getFromCache(keynum);
    }

    @GetMapping("remove/{keynum}")
    public String getRemoveNotEx(@PathVariable Long keynum) {
        cachableService.removeFromCache(keynum);
        return "Removed from cache";
    }
}
