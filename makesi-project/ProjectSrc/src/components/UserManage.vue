<script setup lang="ts">
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { ElMessageBox } from 'element-plus'
import { ElTable } from 'element-plus'

const dialogVisible = ref(false)

const handleClose = (done: () => void) => {
  ElMessageBox.confirm('Are you sure to close this dialog?')
    .then(() => {
      done()
    })
    .catch(() => {
      // catch error
    })
}
function deleteUser(){}
function changeUserAuthority(){}
const searchUsrInput = ref('');
const tableData = []
const isPermission = ref('false');
</script>

<template>
<el-container style="margin-left: 50px; margin-top: 30px;">
      <el-header class="header">
        <el-row>
            <el-col :span="14">
                <el-input
                v-model="searchUsrInput"
                class="w-50 m-2"
                placeholder="请输入用户名"
                :prefix-icon="Search"
                clearable
                />     
            </el-col>
            <el-col :span="4">
                <button>查询</button>
            </el-col>           
            <el-col :span="6">
              <el-switch
              v-model="isPermission"
              size="large"
              active-text="查看申请权限的用户"
              />
            </el-col>
        </el-row>
      </el-header>
      <el-main class="main">
        <div class="line"></div>    
        <el-table :data="tableData" 
          stripe 
          style="width: 94.2%; height:480px;"
          :cell-style="{ textAlign: 'center'}"
          :header-cell-style="{ textAlign: 'center'}"
        >
        <el-table-column fixed prop="username" label="用户名" width="150" />
        <el-table-column v-if="!isPermission" prop="permissiontime" label="用户查看私密文件权限时间" :show-overflow-tooltip="true" width="300" />
        <el-table-column v-if="!isPermission" prop="email" label="邮箱" width="200" :show-overflow-tooltip="true"/>
        <el-table-column v-if="!isPermission" prop="create_time" label="创建时间" width="180" :show-overflow-tooltip="true"/>
        <el-table-column v-if="!isPermission" prop="update_time" label="修改时间" width="180" :show-overflow-tooltip="true"/>
        <el-table-column v-if="isPermission" prop="update_time" label="申请人" width="110" :show-overflow-tooltip="true"/>
        <el-table-column v-if="isPermission" prop="update_time" label="申请权限" width="100" :show-overflow-tooltip="true"/>
        <el-table-column v-if="isPermission" prop="update_time" label="申请理由" width="650" :show-overflow-tooltip="true"/>
        <el-table-column fixed="right" label="操作" width="120">
        <template #default>
            <el-row>
                <el-col :span="12">
                    <el-button link type="primary" size="small" @click="deleteUser">删除</el-button>
                </el-col>
                <el-col :span="12">
                    <el-button link type="primary" size="small" @click="changeUserAuthority">修改权限</el-button>
                </el-col>
            </el-row>
        </template>
        </el-table-column>
        </el-table>
        <el-dialog
        v-model="dialogVisible"
        title="Tips"
        width="600px"
        :before-close="handleClose"
        >
        <span>This is a message</span>
        <template #footer>
        <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="dialogVisible = false">
                    确认
            </el-button>
        </span>
        </template>
        </el-dialog>
    </el-main>
</el-container>
</template>

<style scoped>
.header button{
    display: inline-block;
    background-color:rgb(0, 155, 206);
    border:0;
    color:white;
    border-radius: 4px;
    height: 32px;
    width: 80px;
}

.header button:hover{
    display: inline-block;
    background-color:brown;
    border:0;
    color:white;
    border-radius: 4px;
    height: 32px;
    width: 80px;
}
.header{
    padding-top: 12px;
}

.line{
    height:2px;
    border-top:2px solid brown;
}
.el-table__header-wrapper{
  --el-table-border: 2px solid brown;
}
.el-table{
  --el-table-border: 1px solid rgb(86, 86, 86);
}

</style>