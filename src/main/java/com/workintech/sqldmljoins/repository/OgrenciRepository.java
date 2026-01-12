package com.workintech.sqldmljoins.repository;

import com.workintech.sqldmljoins.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OgrenciRepository extends JpaRepository<Ogrenci, Long> {


    //Kitap alan öğrencilerin öğrenci bilgilerini listeleyin..
    String QUESTION_2 = "SELECT ogrenci.* \n" +
            "FROM public.ogrenci \n" +
            "INNER JOIN public.islem \n" +
            "ON islem.ogrno = ogrenci.ogrno;\n";
    @Query(value = QUESTION_2, nativeQuery = true)
    List<Ogrenci> findStudentsWithBook();


    //Kitap almayan öğrencileri listeleyin.
    String QUESTION_3 = "SELECT ogrenci.* \n" +
            "FROM public.ogrenci \n" +
            "LEFT JOIN public.islem \n" +
            "ON islem.ogrno = ogrenci.ogrno \n" +
            "WHERE islem.ogrno IS NULL;\n";
    @Query(value = QUESTION_3, nativeQuery = true)
    List<Ogrenci> findStudentsWithNoBook();

    //10A veya 10B sınıfındaki öğrencileri sınıf ve okuduğu kitap sayısını getirin.
    String QUESTION_4 = "SELECT ogrenci.sinif, COUNT(islem.kitapno)\n" +
            "FROM ogrenci\n" +
            "LEFT JOIN islem ON islem.ogrno = ogrenci.ogrno\n" +
            "WHERE ogrenci.sinif IN ('10A', '10B')\n" +
            "GROUP BY ogrenci.sinif;\n";
    @Query(value = QUESTION_4, nativeQuery = true)
    List<KitapCount> findClassesWithBookCount();

    //Öğrenci tablosundaki öğrenci sayısını gösterin
    String QUESTION_5 = "SELECT COUNT(ogrenci.ogrno)FROM ogrenci;";
    @Query(value = QUESTION_5, nativeQuery = true)
    Integer findStudentCount();

    //Öğrenci tablosunda kaç farklı isimde öğrenci olduğunu listeleyiniz.
    String QUESTION_6 = "SELECT COUNT(DISTINCT ogrenci.ad)FROM ogrenci;";
    @Query(value = QUESTION_6, nativeQuery = true)
    Integer findUniqueStudentNameCount();

    //--İsme göre öğrenci sayılarının adedini bulunuz.
    //--Ali: 2, Mehmet: 3
    String QUESTION_7 = "SELECT ad, COUNT(ad)FROM ogrenci GROUP BY ad;\n";
    @Query(value = QUESTION_7, nativeQuery = true)
    List<StudentNameCount> findStudentNameCount();


    String QUESTION_8 = "SELECT  sinif,COUNT(ogrenci.ogrno) FROM ogrenci GROUP BY sinif;";
    @Query(value = QUESTION_8, nativeQuery = true)
    List<StudentClassCount> findStudentClassCount();

    String QUESTION_9 = "SELECT ad,soyad,COUNT(islem.kitapno) FROM ogrenci INNER JOIN islem ON islem.ogrno=ogrenci.ogrno GROUP BY ogrenci.ad,ogrenci.soyad;";
    @Query(value = QUESTION_9, nativeQuery = true)
    List<StudentNameSurnameCount> findStudentNameSurnameCount();
}
