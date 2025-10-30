<template>

  <div class="reservation-management">

    <div class="card">

      <div class="card-header">

        <span>预约列表</span>

        <button class="btn btn-primary" @click="fetchList" :disabled="loading">刷新</button>

      </div>



      <div v-if="loading" class="loading-overlay">加载中...</div>

     

      <table v-else class="data-table">

        <thead>

          <tr>

            <th class="col-id">ID</th>

            <th>课程ID</th>

            <th>课程名称</th>

            <th>预约人</th>

            <th>联系电话</th>

            <th>预约时间</th>

            <th>创建时间</th>

            <th class="col-status">状态</th>

            <th class="col-actions">操作</th>

          </tr>

        </thead>

        <tbody>

          <tr v-for="row in tableData" :key="row.id">

            <td class="col-id">{{ row.id }}</td>

            <td>{{ row.courseId }}</td>

            <td>{{ row.courseName }}</td>

            <td>{{ row.name }}</td>

            <td>{{ row.phone }}</td>

            <td>{{ row.appointment }}</td>

            <td>{{ row.createTime }}</td>

            <td :class="['tag', getStatusTagClass(row.status)]">{{ row.status }}</td>

            <td class="col-actions">

              <button

                class="btn btn-action btn-confirm"

                @click="handleUpdateStatus(row.id, '已确认')"

              >

                确认

              </button>

              <button

                class="btn btn-action btn-cancel"

                @click="handleUpdateStatus(row.id, '已取消')"

              >

                取消

              </button>

              <button

                class="btn btn-action btn-delete"

                @click="handleDelete(row.id)"

              >

                删除

              </button>

            </td>

          </tr>

        </tbody>

      </table>



      <div v-if="!loading && tableData.length === 0" class="no-data">

        暂无预约数据

      </div>

    </div>

  </div>

</template>
<script setup>
import { ref, onMounted } from 'vue'
// 注意：移除了对 'element-plus' 的导入

// 假设您的接口文件名为 api/reserve.js
import { getReserveList, deleteReserve, updateReserveStatus } from '@/api/reserve'

// --- 数据状态 ---
const tableData = ref([])
const loading = ref(false)

// --- 列表获取逻辑 ---
const fetchList = async () => {
    loading.value = true
    try {
        const res = await getReserveList()

        // ✅ 第一层检查
        if (res && res.code === 1) {
            // ✅ 第二层检查
            if (res.response && res.response.code === 1) {
                tableData.value = res.response.data || []
                console.log(`列表获取成功: ${res.response.message}`)
            } else {
                alert(`错误：${res.response?.message || '内部数据格式异常'}`)
                tableData.value = []
            }
        } else {
            alert(`错误：获取预约列表失败！原因: ${res.message || '未知错误'}`)
            tableData.value = []
        }
    } catch (error) {
        console.error('获取预约列表失败:', error)
        alert('错误：获取预约列表失败！请检查网络或后端接口。')
    } finally {
        loading.value = false
    }
}


// --- 状态标签样式（返回 CSS Class） ---
const getStatusTagClass = (status) => {
    switch (status) {
        case '待确认':
        case '待处理':
            return 'tag-info'
        case '已确认':
            return 'tag-success'
        case '已取消':
        case '已拒绝':
            return 'tag-danger'
        default:
            return 'tag-default'
    }
}

// --- 更新状态操作 ---
const handleUpdateStatus = async (id, status) => {
    try {
        // 使用浏览器 confirm 替代 ElMessageBox.confirm
        const confirmed = confirm(`确定要将 ID 为 ${id} 的预约状态更新为 [${status}] 吗？`)
        
        if (!confirmed) {
            return // 用户取消操作
        }

        // 调用接口
        const res = await updateReserveStatus(id, status)

        // 核心修改 3: 操作接口成功判断，检查最外层 code 是否为 1
        if (res && res.code === 1) {
            alert(`${status} 成功！`) // 使用 alert 替代 ElMessage.success
            // 刷新列表
            await fetchList()
        } else {
            // 如果操作失败，显示最外层的 message
            alert(`错误：状态更新失败！原因: ${res.message || '未知错误'}`) // 替代 ElMessage.error
        }
    } catch (error) {
        console.error('更新状态失败:', error)
        alert('操作失败，请检查网络或后端接口。')
    }
}

// --- 删除操作 ---
const handleDelete = async (id) => {
    try {
        // 使用浏览器 confirm 替代 ElMessageBox.confirm
        const confirmed = confirm(`警告：确定要删除 ID 为 ${id} 的预约吗？此操作不可逆！`)

        if (!confirmed) {
            return // 用户取消操作
        }

        // 调用接口
        const res = await deleteReserve(id)

        // 核心修改 4: 删除接口成功判断，检查最外层 code 是否为 1
        if (res && res.code === 1) {
            alert('删除成功！') // 替代 ElMessage.success
            // 刷新列表
            await fetchList()
        } else {
            // 如果操作失败，显示最外层的 message
            alert(`错误：删除失败！原因: ${res.message || '未知错误'}`) // 替代 ElMessage.error
        }
    } catch (error) {
        console.error('删除失败:', error)
        alert('操作失败，请检查网络或后端接口。')
    }
}

// --- 生命周期钩子 ---
onMounted(() => {
    fetchList()
})
</script>

<style scoped>
/* --- 布局容器和卡片样式 --- */
.reservation-management {
    padding: 20px;
}
.card {
    border: 1px solid #ebeef5;
    border-radius: 4px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    padding: 20px;
}
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
    font-size: 18px;
    font-weight: bold;
}
.loading-overlay, .no-data {
    text-align: center;
    padding: 20px;
    color: #999;
}

/* --- 按钮样式 --- */
.btn {
    padding: 5px 10px;
    border-radius: 4px;
    cursor: pointer;
    border: 1px solid transparent;
    margin-left: 5px;
}
.btn-primary {
    background-color: #409eff;
    color: white;
}
.btn-action {
    font-size: 12px;
    background: none;
    border: none;
    text-decoration: underline;
    padding: 0 5px;
}
.btn-confirm { color: #67c23a; }
.btn-cancel { color: #e6a23c; }
.btn-delete { color: #f56c6c; }

/* --- 表格样式 --- */
.data-table {
    width: 100%;
    border-collapse: collapse;
    font-size: 14px;
}
.data-table th, .data-table td {
    border: 1px solid #ebeef5;
    padding: 10px;
    text-align: left;
}
.data-table th {
    background-color: #f5f7fa;
    font-weight: bold;
}
.col-id { width: 80px; text-align: center; }
.col-status { width: 100px; text-align: center; }
.col-actions { width: 200px; text-align: center; }

/* --- 状态标签样式 --- */
.tag {
    display: inline-block;
    padding: 4px 8px;
    border-radius: 4px;
    font-size: 12px;
    font-weight: 500;
    white-space: nowrap;
}
.tag-info { background-color: #9093991a; color: #909399; }
.tag-success { background-color: #67c23a1a; color: #67c23a; }
.tag-danger { background-color: #f56c6c1a; color: #f56c6c; }
.tag-default { background-color: #dcdfe61a; color: #606266; }
</style>