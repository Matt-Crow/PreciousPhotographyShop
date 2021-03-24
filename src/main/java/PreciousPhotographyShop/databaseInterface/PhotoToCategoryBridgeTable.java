/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PreciousPhotographyShop.databaseInterface;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Matt
 */
public interface PhotoToCategoryBridgeTable extends CrudRepository<PhotographToCategoryTableEntry, String>{
    Iterable<PhotographToCategoryTableEntry> findAllByPhotographId(String photographId);
    Iterable<PhotographToCategoryTableEntry> findAllByCategoryId(String categoryId);
}