package App.database;

import App.dataModel.ParamAndValueData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * private final SimpleStringProperty param_value_id;
 * private final SimpleStringProperty proj_id;
 * private final SimpleStringProperty version;
 * private final SimpleStringProperty param_id;
 * private final SimpleStringProperty outfitting_name;
 * private final SimpleStringProperty param_name;
 * private final SimpleStringProperty param_type;
 * private final SimpleStringProperty param_description;
 * private final SimpleStringProperty param_value;
 * private final SimpleStringProperty remark;
 */
public class ParamAndValueDatabase extends DatabaseItem {

    /**
     * 根据项目id和版本返回参数结果。
     *
     * @param proj_id 项目id
     * @param version 版本号
     * @return 参数list
     */
    public static List<ParamAndValueData> getParamByProjAndVersion(int proj_id, String version) {
        List<ParamAndValueData> list = new ArrayList<>();

        PreparedStatement ps = null;
        Connection connection = connectDB();
        try {
            ps = connection.prepareStatement("select * from paramandvalue where proj_id = ? and version_name = ?");
            ps.setInt(1, proj_id);
            ps.setString(2, version);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ParamAndValueData paramAndValueData = new ParamAndValueData();

//                paramAndValueData.setParam_value_id(String.valueOf(rs.getInt("id")));
                paramAndValueData.setProj_id(String.valueOf(rs.getInt("proj_id")));
                paramAndValueData.setVersion_name(rs.getString("version_name"));
                paramAndValueData.setParam_id(String.valueOf(rs.getInt("param_id")));
                paramAndValueData.setOutfitting_name(rs.getString("outfitting_name"));
                paramAndValueData.setParam_name(rs.getString("param_name"));
                paramAndValueData.setParam_type(String.valueOf(rs.getInt("param_type")));
                paramAndValueData.setParam_description(rs.getString("param_description"));
                paramAndValueData.setParam_value(rs.getString("param_value"));
//                paramAndValueData.setRemark(rs.getString("remark"));

                list.add(paramAndValueData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return list;
    }

    public static boolean insertAllColumn(List<ParamAndValueData> list) {
        boolean flag = true;
        PreparedStatement ps = null;
        Connection connection = connectDB();
        String sql = "insert into jproject.paramandvalue (proj_id, version_name, param_id, outfitting_name, param_name, param_type, param_description, param_value) value(?, ?, ?, ?, ?, ?, ?, ?)";

        for (ParamAndValueData pvd : list) {
            try {
                ps = connection.prepareStatement(sql);
                ps.setInt(1, Integer.valueOf(pvd.getProj_id()));
                ps.setString(2, pvd.getVersion_name());
                ps.setInt(3, Integer.valueOf(pvd.getParam_id()));
                ps.setString(4, pvd.getOutfitting_name());
                ps.setString(5, pvd.getParam_name());
                if (pvd.getParam_type().equals("已知")) {
                    ps.setInt(6, 0);
                } else if (pvd.getParam_type().equals("待求")) {
                    ps.setInt(6, 1);
                } else {
                    throw new Exception("参数类型输入非法");
                }
                ps.setString(7, pvd.getParam_description());
                ps.setString(8, pvd.getParam_value());
//                ps.setString(9, pvd.getRemark());

                if (ps.executeUpdate() == 0) {
                    flag = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeDatabase(ps, null, connection);
            }
        }
        return flag;
    }

    public static boolean insertValue(List<ParamAndValueData> list) {
        boolean flag = true;
        PreparedStatement ps = null;
        Connection connection = connectDB();
        String sql = "insert into jproject.paramandvalue (proj_id, version_name, param_id, outfitting_name, param_name, param_type, param_description, param_value, remark) value(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return true;
    }

    public static void saveValue(int selectedProjId, String selectedVersionName, List<ParamAndValueData> list) {

    }
}
