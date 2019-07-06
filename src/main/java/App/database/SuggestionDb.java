package App.database;

import App.dataModel.SuggestionData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuggestionDb extends DatabaseItem {

    public static List<String> getShipTypeList() {
        List<String> list = new ArrayList<>();

        Connection connection = connectDB();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("select distinct sugShipType from suggestion");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String temp = resultSet.getString("sugShipType");
                if (!list.contains(temp))
                    list.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return list;
    }

    public static List<String> getChuandongCompanyList() {
        List<String> list = new ArrayList<>();

        Connection connection = connectDB();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("select distinct sugShipCompany from suggestion");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String temp = resultSet.getString("sugShipCompany");
                if (!list.contains(temp))
                    list.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return list;
    }

    /**
     * Return all of the sugData in a list way.
     *
     * @return
     */
    public static ObservableList<SuggestionData> getSugDataList() {
        ArrayList<SuggestionData> sugList = new ArrayList<>();

        Connection connection = connectDB();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("select * from suggestion order by sugId+0");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                SuggestionData suggestionData = new SuggestionData();
                suggestionData.setSugId(resultSet.getString("sugId"));
                suggestionData.setSugShipCompany(resultSet.getString("sugShipCompany"));
                suggestionData.setSugShipType(resultSet.getString("sugShipType"));
                suggestionData.setSugOutfittingRegion(resultSet.getString("sugOutfittingRegion"));
                suggestionData.setSugProblemDescribe(resultSet.getString("sugProblemDescribe"));
                suggestionData.setSugSolutionDescribe(resultSet.getString("sugSolutionDescribe"));
                suggestionData.setSugContent(resultSet.getString("sugContent"));
                suggestionData.setSugFilePath(resultSet.getString("sugFilePath"));
                sugList.add(suggestionData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(ps, null, connection);
        }
        return FXCollections.observableArrayList(sugList);
    }

    /**
     * query the SuggestionTable and return the result of query in observableList way.
     *
     * @param shipCompany
     * @return
     * @throws SQLException
     */
    public static ObservableList<SuggestionData> query(String shipCompany, String shipType, String outfittingRegion, String keyword) throws SQLException {
        ArrayList<SuggestionData> suggestionDataArrayList = new ArrayList<>();

        if (shipCompany == null) shipCompany = "";
        if (shipType == null) shipType = "";
        if (outfittingRegion == null) outfittingRegion = "";
        if (keyword == null) keyword = "";

        Connection connection = connectDB();
        PreparedStatement preparedStatement;

        String sql = "select * from jproject.suggestion where sugShipCompany like ? and sugShipType like ? and sugOutfittingRegion like ? and (sugProblemDescribe like ? or sugSolutionDescribe like ? or sugContent like ?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + shipCompany.replace(" ", "") + "%");
        preparedStatement.setString(2, "%" + shipType.replace(" ", "") + "%");
        preparedStatement.setString(3, "%" + outfittingRegion.replace(" ", "") + "%");
        preparedStatement.setString(4, "%" + keyword.replace(" ", "") + "%");
        preparedStatement.setString(5, "%" + keyword.replace(" ", "") + "%");
        preparedStatement.setString(6, "%" + keyword.replace(" ", "") + "%");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            SuggestionData suggestionData = new SuggestionData();
            suggestionData.setSugId(resultSet.getString("sugId"));
            suggestionData.setSugShipCompany(resultSet.getString("sugShipCompany"));
            suggestionData.setSugShipType(resultSet.getString("sugShipType"));
            suggestionData.setSugOutfittingRegion(resultSet.getString("sugOutfittingRegion"));
            suggestionData.setSugProblemDescribe(resultSet.getString("sugProblemDescribe"));
            suggestionData.setSugSolutionDescribe(resultSet.getString("sugSolutionDescribe"));
            suggestionData.setSugContent(resultSet.getString("sugContent"));
            suggestionData.setSugFilePath(resultSet.getString("sugFilePath"));

            suggestionDataArrayList.add(suggestionData);
        }
        return FXCollections.observableArrayList(suggestionDataArrayList);
    }

    /**
     * insert the new suggestionData.
     *
     * @param sug
     * @return
     */
    public static boolean insert(SuggestionData sug) {
        boolean flag = true;

        Connection connection = connectDB();
        PreparedStatement preparedStatement = null;

        String sql = "insert into suggestion (sugShipCompany, sugShipType, sugOutfittingRegion, sugProblemDescribe, sugSolutionDescribe, sugContent, sugFilePath) value(?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sug.getSugShipCompany());
            preparedStatement.setString(2, sug.getSugShipType());
            preparedStatement.setString(3, sug.getSugOutfittingRegion());
            preparedStatement.setString(4, sug.getSugProblemDescribe());
            preparedStatement.setString(5, sug.getSugSolutionDescribe());
            preparedStatement.setString(6, sug.getSugContent());
            preparedStatement.setString(7, sug.getSugFilePath());

            int i = preparedStatement.executeUpdate();
            if (i == 0) flag = false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(preparedStatement, null, connection);
        }
        if (flag) System.out.println("操作成功！");
        return flag;
    }

    /**
     * Update the content of the selected item.
     *
     * @param sugData
     * @return
     */
    public static boolean update(SuggestionData sugData, String editSugId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "update suggestion set sugShipCompany=?, sugShipType=?, sugOutfittingRegion=?, sugProblemDescribe=?, sugSolutionDescribe=?, sugContent=?, sugFilePath=? where sugId=?";
        Connection connection = connectDB();

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, sugData.getSugShipCompany());
            preparedStatement.setString(2, sugData.getSugShipType());
            preparedStatement.setString(3, sugData.getSugOutfittingRegion());
            preparedStatement.setString(4, sugData.getSugProblemDescribe());
            preparedStatement.setString(5, sugData.getSugSolutionDescribe());
            preparedStatement.setString(6, sugData.getSugContent());
            preparedStatement.setString(7, sugData.getSugFilePath());
            preparedStatement.setString(8, editSugId);

            int i = preparedStatement.executeUpdate();
            if (i == 0) flag = false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(preparedStatement, null, connection);
        }
        if (flag) System.out.println("操作成功！");
        return flag;
    }

    /**
     * Delete the item that has been chosen.
     *
     * @param selectedSugId
     * @return
     */
    public static boolean delete(String selectedSugId) {
        boolean flag = true;

        PreparedStatement preparedStatement = null;

        String sql = "delete from suggestion where sugId = ?";
        Connection connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, selectedSugId);

            int i = preparedStatement.executeUpdate();
            if (i == 0) flag = false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(preparedStatement, null, connection);
        }
        if (flag) System.out.println("操作成功！");
        return flag;
    }
}
