package words.com.wordservice.db.daos.impls;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import words.com.wordservice.db.entities.WordPlayListEntity;
import words.com.wordservice.db.projections.WordPlaylistCountProjection;

import java.util.Collection;
import java.util.Set;


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
                       (:hasIds = true or wp.id in(:ids)) AND
                       (:hasUserIds = true or wp.user_id in(:userIds)) AND
                       (:name IS NULL OR wp.name ILIKE CONCAT('%',:name,'%')) AND
                       (:hasCefrs = true or EXISTS (
                           SELECT 1 FROM jsonb_array_elements_text(wp.cefrs::jsonb) AS elem
                           WHERE elem IN (:cefrs)
                       )) AND
                       (:hasTags = true or EXISTS (
                           SELECT 1 FROM jsonb_array_elements_text(wp.tags::jsonb) AS elem
                           WHERE elem IN (:tags)
                       )) AND
                       (:language IS NULL OR wp.language = :language) AND
                       (:translateLanguage IS NULL OR wp.translate_language = :translateLanguage) AND
                       (:visibility IS NULL OR wp.visibility = :visibility) AND
                       (:associationId IS NULL OR wp.association_id = :associationId) AND
                       (:hasNotInIds = true or wp.id NOT IN (:notInIds))
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
            boolean hasCefrs,
            Collection<String> cefrs,
            boolean hasTags,
            Collection<String> tags,
            String language,
            String translateLanguage,
            String visibility,
            String associationId,
            boolean hasNotInIds,
            Collection<String> notInIds,
            Pageable pageable
    );

    @Query(value = "SELECT wp.baseId FROM WordPlayListEntity wp WHERE wp.userId = :userId and wp.baseId is not null ")
    Set<String> getAssignedPlaylistIds(String userId);

}
