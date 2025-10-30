import { get, postWithLoadTip } from '@/utils/request'

// 后端接口路径
const adminUrl = '/api/reserveadmin' // 管理员接口路径'/api/reserve'
const publicUrl = '/api/admin/reserveadmin'    // 公共接口路径 (用于状态更新)

/**
 * 获取预约列表 (GET /api/reserveadmin/list)
 * 使用 get 方法请求列表数据
 * @returns {Promise<any>} 返回包含预约列表数据的 Promise
 */
export function getReserveList() {
  // 对应后端 @GetMapping("/list")
  return get(publicUrl + '/list')
}

/**
 * 删除预约 (POST /api/reserveadmin/delete/{id})
 * 使用 postWithLoadTip 方法执行删除操作，并带有加载提示
 * @param {number} id 要删除的预约的ID
 * @returns {Promise<any>} 返回删除结果的 Promise
 */
export function deleteReserve(id) {
  // 对应后端 @PostMapping("/delete/{id}")
  return postWithLoadTip(publicUrl + `/delete/${id}`, null)
}

// ===========================================
// 新增：更新预约状态接口
// ===========================================

/**
 * 更新预约状态 (POST /api/reserve/updateStatus)
 * 使用 postWithLoadTip 方法执行状态更新操作，并带有加载提示。
 * 注意：该接口路径为 /api/reserve (公共接口)。
 * * @param {number} id - 预约的ID
 * @param {string} status - 要更新到的状态值（如：'已确认', '已取消'）
 * @returns {Promise<any>} 返回更新结果的 Promise
 */
export function updateReserveStatus(id, status) {
  const params = {
    id: id,
    status: status
  }
  // 对应后端 @PostMapping("/updateStatus")
  return postWithLoadTip(publicUrl + '/updateStatus', params)
}

// 示例：如果未来后端改为 RESTful DELETE /api/reserveadmin/{id}
/*
// import { deleteWithLoadTip } from '@/utils/request'
// export function deleteReserveRestful(id) {
//   // 对应 RESTful DELETE 接口
//   return deleteWithLoadTip(adminUrl + `/${id}`, null)
// }
*/
