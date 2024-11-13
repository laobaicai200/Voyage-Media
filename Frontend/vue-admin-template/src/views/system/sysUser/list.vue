<template>
  <div class="app-container">
    <!--搜索条件查询-->
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="8">
            <el-form-item label="关 键 字">
              <el-input style="width: 95%" v-model="searchObj.keyword" placeholder="用户名/姓名/手机号码"
                @input="fetchData()"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="操作时间">
              <el-date-picker v-model="createTimes" type="datetimerange" range-separator="至" start-placeholder="开始时间"
                end-placeholder="结束时间" value-format="yyyy-MM-dd HH:mm:ss" style="margin-right: 10px;width: 100%;"
                @change="fetchData()" />
            </el-form-item>
          </el-col>
        </el-row>
        <div>
          <div class="tools-div">
            <!-- 添加 -->
            <el-button type="success" icon="el-icon-plus" size="mini" @click="add" :disabled="$hasBP('bnt.sysUser.add')  === false">添 加</el-button>
            <!-- 批量删除的按钮 -->
            <el-button type="danger" size="mini" @click="batchRemove()" icon="el-icon-delete" :disabled="$hasBP('bnt.sysUser.remove')  === false">批量删除</el-button>
            <el-row style="display: flex; justify-content: space-between; align-items: center;">
              <div></div> <!-- 占位符，用于对齐 -->
              <div style="display: flex;">
                <el-button type="primary" icon="el-icon-search" size="mini" @click="fetchData()">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetData">重置</el-button>
              </div>
            </el-row>
          </div>
        </div>
      </el-form>
    </div>
    <!-- 列表 -->
    <el-table v-loading="listLoading" :data="list" stripe border style="width: 100%;margin-top: 10px;"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" />
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="username" label="用户名" width="180" />
      <el-table-column prop="name" label="姓名" width="110" />
      <el-table-column prop="phone" label="手机" />
      <el-table-column label="状态" width="80">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status === 1" @change="switchStatus(scope.row)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" />

      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)" title="修改" :disabled="$hasBP('bnt.sysUser.update')  === false" />
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeDataById(scope.row.id)" title="删除" :disabled="$hasBP('bnt.sysUser.remove')  === false" />
          <el-button type="warning" icon="el-icon-baseball" size="mini" @click="showAssignRole(scope.row)"title="分配角色" :disabled="$hasBP('bnt.sysUser.assignRole')  === false" />
        </template>
      </el-table-column>
    </el-table>



    <!-- 分页组件 -->
    <el-pagination :current-page="page" :total="total" :page-size="limit" style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper" @current-change="fetchData" />

    <!--添加/修改弹框-->
    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%">
      <el-form ref="dataForm" :model="sysUser" label-width="100px" size="small" style="padding-right: 40px;">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="sysUser.username" />
        </el-form-item>
        <el-form-item  label="密码" prop="password">
          <el-input v-model="sysUser.password" type="password" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="sysUser.name" />
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input v-model="sysUser.phone" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel()" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" @click="saveOrUpdate()" size="small">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 分配角色对话框 -->
    <el-dialog title="分配角色" :visible.sync="dialogRoleVisible">
      <el-form label-width="80px">
        <el-form-item label="用户名">
          <el-input disabled :value="sysUser.username"></el-input>
        </el-form-item>

        <el-form-item label="角色列表">
          <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll"
            @change="handleCheckAllChange">全选</el-checkbox>
          <div style="margin: 15px 0;"></div>
          <el-checkbox-group v-model="userRoleIds" @change="handleCheckedChange">
            <el-checkbox v-for="role in allRoles" :key="role.id" :label="role.id">{{ role.roleName }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="assignRole" size="small">保存</el-button>
        <el-button @click="dialogRoleVisible = false" size="small">取消</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
import api from '@/api/user/user'
import roleApi from '@/api/role/role.js'
export default {
  data() {
    return {
      listLoading: true, // 数据是否正在加载
          list: [], // 数据列表
          total: 0, // 数据库中的总记录数
          page: 1, // 默认页码
          limit: 3, // 每页记录数
          searchObj: {}, // 查询表单对象
          // 处理时间范围条件的
          createTimes: [],
          dialogVisible: false,
          sysUser: {},
          saveBtnDisabled: false,
          // 多选
          selectValueData: [],

          dialogRoleVisible: false,
          allRoles: [], // 所有角色列表
          userRoleIds: [], // 用户的角色ID的列表
          isIndeterminate: false, // 是否是不确定的
          checkAll: false // 是否全选

    }
  },
  created() {
    this.fetchData();
  },
  methods: {
    // 修改状态
    switchStatus(row) {
      // 如果 可用  设置为 不可用  如果不可用  设置为 可用
      row.status = row.status === 1 ? 0 : 1;
      api.updateStatusById(row.id,row.status).then(response => {
        //a.给出提示
        this.$message.success(response.message || '操作成功');
        //b.再次刷新列表
        this.fetchData();
      })
    },
    edit(id) {
      this.dialogVisible = true;
      api.getUserById(id).then(response => {
        this.sysUser = response.data;
      })
    },
    add() {
      this.sysUser= {}
      this.dialogVisible = true;
    },
    // 添加或者修改 具体功能
    saveOrUpdate() {
      if (this.sysUser.id != null) {
        this.updateUser();
      }
      else {
        this.addUser();
      }
    },
    updateUser() {
      api.updateUser(this.sysUser)
        .then(response => {
          //给出提示框
          this.$message({
            type: "success",
            message: "修改成功!",
          });
          // 关闭弹框
          this.dialogVisible = false;
          // 刷新数据
          this.fetchData();

        })
    },
    // 实现添加功能
    addUser() {
      api.saveUser(this.sysUser)
        .then(response => {
          this.$message({
            type: "success",
            message: "添加成功!",
          });
          // 关闭添加弹框
          this.dialogVisible = false;
          // 再次刷新数据
          this.fetchData();
        })
    },
    fetchData(page = 1) {
      this.page = page;
      if (this.createTimes && this.createTimes.length == 2) {
        this.searchObj.createTimeBegin = this.createTimes[0];
        this.searchObj.createTimeEnd = this.createTimes[1];
      }
      api.getPageList(this.page, this.limit, this.searchObj).then(
        response => {
          this.list = response.data.records;
          this.total = response.data.total;
          // 数据加载并绑定成功
          this.listLoading = false;
        }
      )
    },
    resetData() {
      // 1.清空条件搜索
      this.searchObj = {};
      //2.清空日期条件
      this.createTimes = [];
      //3.刷新数据列表
      this.fetchData();
    },
    //删除功能
    removeDataById(id) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        api.removeId(id).then(response => {
        })
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
        console.log("删除成功");
        this.fetchData();
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    cancel() {
      this.dialogVisible = false;
      this.sysUser = {};
      this.$message({
        type: "info",
            message: "取消操作!",
          });
          
    },
    batchRemove() {
      // 判断是否有选中select
      if (this.selectValueData.length == 0) {
        this.$message.warning('请选择要删除的记录！')
        return;
      }
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        var ids = [];
        for (var i = 0; i < this.selectValueData.length; i++) {
          var obj = this.selectValueData[i];
          // 获取id值
          var id = obj.id;
          // 将id放进到数组中
          ids.push(id);
        }
        api.bactchremoveId(ids)
          .then((response) => {
            // 提示
            this.$message({
              type: "success",
              message: "删除成功!",
            });
            // 刷新页面
            this.fetchData();
          });
      });
    },
    handleSelectionChange(selectValue) {
      this.selectValueData = selectValue;
    },

    //展示分配角色
    showAssignRole(row) {
      this.sysUser = row
      this.dialogRoleVisible = true
      console.log(row.id);
      roleApi.getRolesByUserId(row.id).then(response => {
        console.log(response.data);
        this.allRoles = response.data.allRoles
        this.userRoleIds = response.data.userRoleIds
        this.checkAll = this.userRoleIds.length === this.allRoles.length
        this.isIndeterminate = this.userRoleIds.length > 0 && this.userRoleIds.length < this.allRoles.length
      })
    },

    /*
    全选勾选状态发生改变的监听
    */
    handleCheckAllChange(value) {// value 当前勾选状态true/false
      // 如果当前全选, userRoleIds就是所有角色id的数组, 否则是空数组
      this.userRoleIds = value ? this.allRoles.map(item => item.id) : []
      // 如果当前不是全选也不全不选时, 指定为false
      this.isIndeterminate = false
    },

    /*
    角色列表选中项发生改变的监听
    */
    handleCheckedChange(value) {
      const { userRoleIds, allRoles } = this
      this.checkAll = userRoleIds.length === allRoles.length && allRoles.length > 0
      this.isIndeterminate = userRoleIds.length > 0 && userRoleIds.length < allRoles.length
    },

    //分配角色
    assignRole() {
      let assginRoleVo = {
        userId: this.sysUser.id,
        roleIdList: this.userRoleIds
      }
      console.log("assginRoleVo",assginRoleVo);
      
      roleApi.assignRoles(assginRoleVo).then(response => {
        this.$message.success(response.message || '分配角色成功')
        this.dialogRoleVisible = false
        this.fetchData(this.page)
      })
    },

  }
}
</script>

<style scoped>
.tools-div {
  display: flex;
  align-items: center;
}


.el-button {
  margin: 0 5px;
}
</style>