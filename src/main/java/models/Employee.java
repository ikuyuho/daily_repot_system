package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 従業員データのDTOモデル
 *
 */

//ENTITY_EMP = "employee"

@Table(name = JpaConst.TABLE_EMP) //"employee"
@NamedQueries({
    @NamedQuery(
//            全ての従業員情報を取得
            name = JpaConst.Q_EMP_GET_ALL,  //"employee.getAll"
            query = JpaConst.Q_EMP_GET_ALL_DEF),  //"SELECT e FROM Employee AS e ORDER BY e.id DESC
    @NamedQuery(
//            従業員情報の全件数を取得
            name = JpaConst.Q_EMP_COUNT,  //"employee.count"
            query = JpaConst.Q_EMP_COUNT_DEF),
    @NamedQuery(
//            指定された社員番号が既にデータベースに存在しているか調べる
            name = JpaConst.Q_EMP_COUNT_REGISTERED_BY_CODE, // "employee.countRegisteredByCode"
            query = JpaConst.Q_EMP_COUNT_REGISTERED_BY_CODE_DEF), // "SELECT COUNT(e) FROM Employee AS e WHERE e.code = :" + JPQL_PARM_CODE;;
    @NamedQuery(
//            従業員がログインするときに社員番号とパスワードが正しいかチェックするためのモノ
            name = JpaConst.Q_EMP_GET_BY_CODE_AND_PASS,  //  "employee.getByCodeAndPass"
            query = JpaConst.Q_EMP_GET_BY_CODE_AND_PASS_DEF) //    Q_EMP_GET_BY_CODE_AND_PASS_DEF = "SELECT e FROM Employee AS e WHERE e.deleteFlag = 0 AND e.code = :" + JPQL_PARM_CODE + " AND e.password = :" + JPQL_PARM_PASSWORD;

})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Employee {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.EMP_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 社員番号
     */
    @Column(name = JpaConst.EMP_COL_CODE, nullable = false, unique = true)
//    ↑のunique=trueは一意制約といい、既に存在している社員番号は登録できない旨をデータベースに指定するための設定
    private String code;

    /**
     * 氏名
     */
    @Column(name = JpaConst.EMP_COL_NAME, nullable = false)
    private String name;

    /**
     * パスワード
     */
    @Column(name = JpaConst.EMP_COL_PASS, length = 64, nullable = false)
//    パスワードの最大文字数が64文字
    private String password;

    /**
     * 管理者権限があるかどうか（一般：0、管理者：1）
     */
    @Column(name = JpaConst.EMP_COL_ADMIN_FLAG, nullable = false)
    private Integer adminFlag;

    /**
     *登録日時
     */
    @Column(name = JpaConst.EMP_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.EMP_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 削除された従業員かどうか（現役：0、削除済み：1）
     */
    @Column(name = JpaConst.EMP_COL_DELETE_FLAG, nullable = false)
    private Integer deleteFlag;

}