package words.com.wordservice.db.daos.impls;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import words.com.wordservice.db.entities.WordPlayListEntity;
import words.com.wordservice.db.projections.WordPlaylistCountProjection;

import java.util.Collection;


interface WordPlayListRepository extends ListCrudRepository<WordPlayListEntity, String>,
        JpaSpecificationExecutor<WordPlayListEntity> {

    String SELECT_COUNT =
            """
                    SELECT\s
                        wp.id,
                        wp.user_id AS userId,
                        wp.name,
                        wp.created_at AS createdAt,
                        COUNT(pw.user_word_id) as count,
                        wp.tags,
                        wp.cefrs,
                        wp.language,
                        wp.translate_language AS translateLanguage
                    
                    FROM
                        word_playlists wp
                    LEFT JOIN
                        pinned_word pw ON wp.id = pw.play_list_id
                    WHERE
                       (:hasIds or wp.id in(:ids)) AND
                       (:hasUserIds or wp.user_id in(:userIds)) AND
                       (:name IS NULL OR wp.name LIKE CONCAT('%',:name,'%'))\s
                    GROUP BY  wp.id
                    HAVING
                        (:toCount is null or COUNT(pw.user_word_id) < :toCount) AND
                        (:fromCount is null or COUNT(pw.user_word_id) > :fromCount)
                    """;

    @Query(value = SELECT_COUNT, nativeQuery = true)
    Page<WordPlaylistCountProjection> findPlayListsWithCount(
            boolean hasIds,
            Collection<String> ids,
            boolean hasUserIds,
            Collection<String> userIds,
            String name,
            Long toCount,
            Long fromCount,
            Pageable pageable
    );

}
