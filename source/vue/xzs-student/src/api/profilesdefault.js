import { postWithOutLoadTip } from '@/utils/request' // 假设 postWithOutLoadTip 可用

const base_profile_url = 'api/student/profiles' // 对应 Java Controller 的 @RequestMapping("student/profiles")

/**
 * 仅通过用户ID创建默认用户档案
 * POST /student/profiles/default-by-user/{userId}
 * 对应 Java Controller: createDefault
 * @param {number} userId 要创建档案的用户ID
 * @returns {Promise<Object>} 新创建的用户档案对象 (UserProfile)
 */
export function createDefaultProfileByUserId (userId) {
  // 注意：数据通过 URL 路径传递，请求体为 null
  return postWithOutLoadTip(`${base_profile_url}/default-by-user/${userId}`, null)
}