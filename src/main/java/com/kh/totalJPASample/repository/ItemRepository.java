package com.kh.totalJPASample.repository;
import com.kh.totalJPASample.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository //bin에 등록해야하니까. 없어도 동작하긴 함
// 기본적인 CRUD는 JPARepository에 이미 정의되어 있음. 페이징 처리도 포함되어 있음.
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByItemName(String itemName); //findByItemName 쿼리 만

    //OR 조건처리
    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);

    //LessThan 조건 처리 : price 변수보다 값이 작은 것 조회
    List<Item> findByPriceLessThan(Integer price);

    //OrderBy로 정렬하기
    List<Item> findAllByOrderByPriceDesc();

    //JPQL 쿼리 작성하기 : SQL과 유사한 객체지향 쿼리 언어
    //Item가 대문자로 시작 쿼리 날릴때 클래스이름으로 날리고 필드이름으로 날려야함. db로 날리지 않음. entity쪽으로 날림
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> priceSorting(@Param("itemDetail")String itemDetail);

    // nativeQuery 사용하기 // 실제 db에 쿼리 날릴거임, 위의 것과 다르다. join 문 등이 다름
    @Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc",
    nativeQuery = true)
    List<Item> priceSortingNative(@Param("itemDetail")String itemDetail);

}
